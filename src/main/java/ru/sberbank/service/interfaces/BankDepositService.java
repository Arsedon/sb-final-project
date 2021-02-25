package ru.sberbank.service.interfaces;

import org.springframework.ui.Model;
import ru.sberbank.entity.client.User;

import java.math.BigDecimal;

public interface BankDepositService {

    void bankDepositList(Long id, Model model);

    void createBankDeposit(User client, long id, BigDecimal moneyAmount, int term);

    void depositRateEditForm(Model model);

    void editDepositRate(Long id, String name, double interestRate, int maxTerm, int minTerm,
                         Boolean replenish, Boolean adding, Boolean earlyClosing,
                         Boolean capitalize, Boolean renewable, Model model);

    void clientDepositList(User user, Model model);

    void takePercentsFromDeposit(Long id, BigDecimal moneyAmount, User user, Model model);

    void closeDeposit(Long id, User user, Model model);

    void addMoneyToDeposit(Long id, User user, BigDecimal moneyAmount, Model model);

}
