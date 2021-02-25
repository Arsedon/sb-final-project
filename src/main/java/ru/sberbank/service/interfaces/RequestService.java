package ru.sberbank.service.interfaces;

import org.springframework.ui.Model;
import ru.sberbank.entity.requests.CreditRequest;

import java.math.BigDecimal;

public interface RequestService {

    void requestList(Model model);

    void requestEditForm(Long id, Model model);

    void requestSave(Double interestRate, CreditRequest request);

    void creditCardCreate(CreditRequest request, String number, String pinCode, BigDecimal limitOfCredits, Model model);
}
