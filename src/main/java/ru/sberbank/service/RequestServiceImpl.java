package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.sberbank.entity.Credit;
import ru.sberbank.entity.CreditCard;
import ru.sberbank.entity.Currency;
import ru.sberbank.entity.requests.CreditRequest;
import ru.sberbank.exceptions.ExceedingLimitException;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.repository.CreditRepo;
import ru.sberbank.repository.requests.CreditRequestRepo;
import ru.sberbank.service.interfaces.RequestService;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    @Autowired
    private CreditRequestRepo creditRequestRepo;

    @Autowired
    private CreditRepo creditRepo;

    @Override
    public void requestList(Model model) {
        model.addAttribute("request", creditRequestRepo.findAll());
    }

    @Override
    public void requestEditForm(Long id, Model model) {
        CreditRequest request = creditRequestRepo.getOne(id);
        model.addAttribute("user", request.getClient());
        model.addAttribute("sum of credit", request.getRequestedSum());
        model.addAttribute("request", request);
    }

    @Override
    public void requestSave(Double interestRate, CreditRequest request) {

        if (interestRate == null) {
            throw new RequestParamException();
        }

        CreditCard creditCard = request.getClient().getCreditCard();
        BigDecimal sumOfAllCredits = calculateSumOfAllCredits(creditCard)
                .add(calculateMonthlyPayment(request, interestRate)).setScale(5, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal limitOfCredits = creditCard.getLimitOfCredits().setScale(5, BigDecimal.ROUND_HALF_DOWN);
        int c = limitOfCredits.compareTo(sumOfAllCredits);
        if (limitOfCredits.compareTo(sumOfAllCredits) == 1 || limitOfCredits.compareTo(sumOfAllCredits) == 0) {
            request.setInterestRate(interestRate);
            calculateFinalSumOfCredit(request, interestRate);
            creditRequestRepo.save(request);
        } else {
            creditRequestRepo.delete(request);
            throw new ExceedingLimitException();
        }
    }

    private void calculateFinalSumOfCredit(CreditRequest request, Double interestRate) {
        BigDecimal finalSum;
        if (request.getCurrency().contains(Currency.RUB)) {
            BigDecimal monthlyPayment = calculateMonthlyPayment(request, interestRate);
            finalSum = monthlyPayment;
            request.setFinalSum(finalSum);
        } else if (request.getCurrency().contains(Currency.EUR)) {
            BigDecimal monthlyPayment = calculateMonthlyPayment(request, interestRate);
            finalSum = monthlyPayment.multiply(BigDecimal.valueOf(87.4 * (1 + request.getClient().getBankCommission() / 100)));
            request.setRequestedSum(request.getRequestedSum().multiply(BigDecimal.valueOf(87.4)));
            request.setFinalSum(finalSum);
        } else if (request.getCurrency().contains(Currency.USD)) {
            BigDecimal monthlyPayment = calculateMonthlyPayment(request, interestRate);
            finalSum = monthlyPayment.multiply(BigDecimal.valueOf(74.2 * (1 + request.getClient().getBankCommission() / 100)));
            request.setRequestedSum(request.getRequestedSum().multiply(BigDecimal.valueOf(74.2)));
            request.setFinalSum(finalSum);
        }
    }

    @Transactional
    @Override
    public void creditCardCreate(CreditRequest request,
                                 String number,
                                 String pinCode,
                                 BigDecimal limitOfCredits,
                                 Model model) {
        if (number.length() != 8) {
            throw new IllegalArgumentException("Неверная величина добавочного номера карты!");
        }
        if (pinCode.length() != 4) {
            throw new IllegalArgumentException("Неверная величина пин-кода!");
        }

        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(number);
        creditCard.setPinCode(pinCode);
        creditCard.setClient(request.getClient());
        creditCard.setLimitOfCredits(limitOfCredits);
        request.setCardOfClient(creditCard);

        creditRequestRepo.save(request);
    }

    private BigDecimal calculateMonthlyPayment(CreditRequest request, Double interestRate) {
        Double monthlyRate = (interestRate) / 100;
        Double annuityCoefficient = (monthlyRate * Math.pow(1 + monthlyRate, 1))
                / (Math.pow(1 + monthlyRate, 1) - 1);
        return request.getRequestedSum().multiply(BigDecimal.valueOf(annuityCoefficient));
    }

    private BigDecimal calculateSumOfAllCredits(CreditCard creditCard) {
        Set<Credit> credits = creditCard.getCredits();
        BigDecimal finalSum = BigDecimal.valueOf(0);
        for (Credit c : credits) {
            finalSum = finalSum.add(c.getFinalSum());
        }
        return finalSum;
    }
}
