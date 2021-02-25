package ru.sberbank.service.interfaces;

import org.springframework.ui.Model;
import ru.sberbank.entity.client.User;

import java.math.BigDecimal;

public interface TransferService {

    void requestList(Model model);

    void createTransferRequest(User user, BigDecimal moneyAmount, String cardNumber, Model model);

    void addTransferToClient(Long id, Model model);

    void deleteRequest(Long id, Model model);

    void transferHistory(User user, Model model);
}
