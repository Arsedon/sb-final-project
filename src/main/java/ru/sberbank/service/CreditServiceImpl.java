package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.sberbank.entity.Credit;
import ru.sberbank.entity.CreditCard;
import ru.sberbank.entity.client.User;
import ru.sberbank.exceptions.BadBalanceException;
import ru.sberbank.exceptions.NonEmptyFromCreditsException;
import ru.sberbank.repository.CreditCardRepo;
import ru.sberbank.repository.CreditRepo;
import ru.sberbank.repository.UserRepo;
import ru.sberbank.service.interfaces.CreditService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditRepo creditRepo;

    @Autowired
    private CreditCardRepo creditCardRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public void requestList(User client, Model model) {
        model.addAttribute("user", client);
        model.addAttribute("credits", creditRepo.findByClient(client));
        model.addAttribute("cards", creditCardRepo.findByClient(client));
    }

    @Override
    public void creditRepay(Long id, Model model, BigDecimal payment) {
        Credit credit = creditRepo.getOne(id);
        User client = credit.getClient();
        if (client.getBankAccount().getMoney().compareTo(payment) >= 0) {
            moneyTaking(client, payment);
            model.addAttribute("user", client);
            BigDecimal finalSum = credit.getFinalSum();
            credit.setFinalSum(finalSum.subtract(payment));
            if (credit.getFinalSum().compareTo(BigDecimal.valueOf(0)) <= 0) {
                credit.setClient(null);
                creditRepo.delete(credit);
            } else {
                creditRepo.save(credit);
            }
        } else {
            throw new BadBalanceException();
        }
    }

    @Transactional
    @Override
    public void creditCardClose(User client, Long id, Model model) {

        CreditCard creditCard = client.getCreditCard();

        if (creditsChecking(creditCard)) {

            creditCard.setActive(false);

            creditCardRepo.save(creditCard);
            model.addAttribute("delete", "Ваша карта успешно заблокирована.");

        } else throw new NonEmptyFromCreditsException();
    }

    private void moneyTaking(User client, BigDecimal money) {
        BigDecimal cMoney = client.getBankAccount().getMoney();
        client.getBankAccount().setMoney(cMoney.subtract(money));
        userRepo.save(client);
    }

    private boolean creditsChecking(CreditCard creditCard) {

        if (creditCard.getCredits().isEmpty()) {

            return true;

        } else {
            for (Credit c : creditCard.getCredits()) {
                if (c.getFinalSum().compareTo(BigDecimal.valueOf(0)) > 0) {

                    return false;
                }
            }

            return true;
        }

    }

    @Scheduled(cron = "0 0 12 * * *")
    public void creditCardCloseOnTime() {

        List<CreditCard> creditCards = creditCardRepo.findAll();

        List<CreditCard> overdueCreditCards = creditCards.stream()
                .filter((CreditCard card) -> LocalDate.now().isAfter(card.getValidUntil()))
                .filter((CreditCard card) -> card.getCredits().size() == 0)
                .collect(Collectors.toList());

        closeCards(overdueCreditCards);

        List<CreditCard> overdueCreditCardsWithCredits = creditCards.stream()
                .filter((CreditCard card) -> LocalDate.now().isAfter(card.getValidUntil()))
                .filter((CreditCard card) -> card.getCredits().size() > 0)
                .collect(Collectors.toList());

        prolongCards(overdueCreditCardsWithCredits);

    }

    private void closeCards(List<CreditCard> creditCards) {

        for (CreditCard card : creditCards) {
            card.setActive(false);
            creditCardRepo.save(card);
        }
    }

    private void prolongCards(List<CreditCard> creditCards) {

        for (CreditCard card : creditCards) {
            card.setValidUntil(LocalDate.now().plusDays(30));
            creditCardRepo.save(card);
        }

    }

    @Scheduled(cron = "0 0 12 * * *")
    public void penaltyCalculate() {

        List<Credit> credits = creditRepo.findAll();

        List<Credit> badCredits = credits.stream()
                .filter((Credit c) -> LocalDate.now().isAfter(c.getMaturity()))
                .filter((Credit c) -> c.getFinalSum().compareTo(calculatePenaltyThreshold(c)) < 1)
                .collect(Collectors.toList());

        badCreditPenaltyCalculate(badCredits);

    }

    private BigDecimal calculatePenaltyThreshold(Credit credit) {

        BigDecimal penaltyThreshold = credit.getRequestedSum().
                multiply(BigDecimal.valueOf(100).add(BigDecimal.valueOf(credit.getInterestRate()))).
                multiply(BigDecimal.valueOf(1.4)).
                divide(BigDecimal.valueOf(100));

        return penaltyThreshold;

    }

    private void badCreditPenaltyCalculate(List<Credit> credits) {

        for (Credit credit : credits) {

            BigDecimal penalty = credit.getRequestedSum().
                    multiply(BigDecimal.valueOf((100 + credit.getPenaltyValue()) / 100)).
                    subtract(credit.getRequestedSum());

            credit.setFinalSum(credit.getFinalSum().add(penalty));

            creditRepo.save(credit);

        }
    }
}
