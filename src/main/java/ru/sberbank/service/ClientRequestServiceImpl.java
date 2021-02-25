package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.sberbank.entity.Credit;
import ru.sberbank.entity.Currency;
import ru.sberbank.entity.client.User;
import ru.sberbank.entity.requests.CreditRequest;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.repository.CreditRepo;
import ru.sberbank.repository.UserRepo;
import ru.sberbank.repository.requests.CreditRequestRepo;
import ru.sberbank.service.interfaces.ClientRequestService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ClientRequestServiceImpl implements ClientRequestService {

    @Autowired
    private CreditRequestRepo creditRequestRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CreditRepo creditRepo;

    @Override
    public void requestList(Model model, User client) {
        model.addAttribute("request", creditRequestRepo.findCalculatedRequestsForClient(client.getId()));
    }

    @Override
    public void deleteRequest(Long id, Model model) {
        CreditRequest creditRequest = creditRequestRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        creditRequest.setFinalSum(BigDecimal.valueOf(0));
        creditRequest.setInterestRate(0);
        creditRequestRepo.save(creditRequest);
        model.addAttribute("requestDelete", creditRequest);
    }

    @Override
    public void addCreditToClient(Long id, Model model) {

        CreditRequest creditRequest = creditRequestRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        Credit creditForClient = creditCreating(creditRequest);
        User userForCredit = userRepo.getOne(creditRequest.getClient().getId());

        userForCredit.getCreditCard().getCredits().add(creditForClient);
        userForCredit.getBankAccount().moneyAdding(creditRequest.getRequestedSum());

        userRepo.save(userForCredit);
        creditRequestRepo.delete(creditRequest);
        model.addAttribute("addedRequest", creditRequest);
    }


    private Credit creditCreating(CreditRequest creditRequest) {
        Credit creditForClient = new Credit();

        creditForClient.setRequestedSum(creditRequest.getRequestedSum());
        creditForClient.setClient(creditRequest.getClient());
        creditForClient.setFinalSum(creditRequest.getFinalSum());
        creditForClient.setInterestRate(creditRequest.getInterestRate());
        LocalDate dateOfTaking = LocalDate.now();
        creditForClient.setDateOfTakingCredit(dateOfTaking);
        creditForClient.setMaturity(dateOfTaking.plusDays(30));
        creditForClient.setCreditCard(creditRequest.getCardOfClient());
        creditForClient.setPenaltyValue(creditRequest.getPenaltyValue());
        return creditForClient;
    }

    public void createCreditRequest(User user, BigDecimal sum, Map<String, String> form, Model model) {

        if (sum == null) {
            throw new RequestParamException();
        }

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setClient(user);
        creditRequest.setRequestedSum(sum);

        if (user.getCreditCard() != null) {

            creditRequest.setCardOfClient(user.getCreditCard());

        }

        if (form.containsKey("RUB") || form.containsKey("EUR") || form.containsKey("USD")) {

            Set<String> Currencies = Arrays.stream(Currency.values())
                    .map(Currency::name)
                    .collect(Collectors.toSet());

            for (String key : form.keySet()) {
                if (Currencies.contains(key)) {

                    Set<Currency> curr = new HashSet<>();
                    curr.add(Currency.valueOf(key));
                    creditRequest.setCurrency(curr);

                }
            }

            creditRequestRepo.save(creditRequest);
            model.addAttribute("message", "Успешное создание заявки!");


        } else {
            model.addAttribute("message", "Некоректное заполнение полей, вернитесь назад и повторите попытку!");

        }
    }
}
