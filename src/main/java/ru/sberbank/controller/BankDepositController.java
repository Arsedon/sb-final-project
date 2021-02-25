package ru.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.entity.client.User;
import ru.sberbank.exceptions.BadBalanceException;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.service.BankDepositServiceImpl;

import java.math.BigDecimal;

@Controller
public class BankDepositController {

    @Autowired
    private BankDepositServiceImpl bankDepositServiceImpl;


    @GetMapping("/main/{id}")
    public String bankDepositList(@PathVariable Long id, Model model) {

        bankDepositServiceImpl.bankDepositList(id, model);

        return "/deposits/depositList";
    }

    @PostMapping("/main/{id}")
    public String createBankDeposit(@AuthenticationPrincipal User client,
                                    @PathVariable long id,
                                    @RequestParam(defaultValue = "0") BigDecimal moneyAmount,
                                    @RequestParam(defaultValue = "0") int term,
                                    Model model) {


        try {
            bankDepositServiceImpl.createBankDeposit(client, id, moneyAmount, term);

        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "Проверьте правльность заполнения форм!");
            return "deposits/depositList";
        } catch (BadBalanceException e) {
            model.addAttribute("message", e.getMessage());
            return "deposits/depositList";
        }
        model.addAttribute("message", "Успешное создание вклада!");
        return "/deposits/depositList";
    }

    @GetMapping("/depositRateEdit")
    public String depositRateEditForm(Model model) {
        bankDepositServiceImpl.depositRateEditForm(model);
        return "deposits/rateEditForm";
    }

    @PostMapping("/depositRateEdit/{id}")
    public String editDepositRate(@PathVariable Long id,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) Double interestRate,
                                  @RequestParam(required = false) Integer maxTerm,
                                  @RequestParam(required = false) Integer minTerm,
                                  @RequestParam(required = false) Boolean replenish,
                                  @RequestParam(required = false) Boolean adding,
                                  @RequestParam(required = false) Boolean earlyClosing,
                                  @RequestParam(required = false) Boolean capitalize,
                                  @RequestParam(required = false) Boolean renewable, Model model) {

        if (name == null || interestRate == null || maxTerm == null || minTerm == null) {
            model.addAttribute("message", "Заполните все поля");
            return "deposits/rateEditForm";
        }

        bankDepositServiceImpl.editDepositRate(id, name, interestRate,
                maxTerm, minTerm, replenish,
                adding, earlyClosing, capitalize,
                renewable, model);

        model.addAttribute("message", "Тариф изменен");
        return "deposits/rateEditForm";
    }

    @GetMapping("/listOfDeposits")
    public String clientDepositList(@AuthenticationPrincipal User user, Model model) {
        bankDepositServiceImpl.clientDepositList(user, model);
        return "deposits/clientDepositList";
    }

    @PostMapping("/listOfDeposits/{id}/taking")
    public String takePercentsFromDeposit(@PathVariable Long id,
                                          @RequestParam(required = false) BigDecimal moneyAmount,
                                          @AuthenticationPrincipal User user,
                                          Model model) {

        try {
            bankDepositServiceImpl.takePercentsFromDeposit(id, moneyAmount, user, model);

            return "deposits/clientDepositList";

        } catch (IllegalArgumentException | RequestParamException e) {

            model.addAttribute("message", e.getMessage());

            return "deposits/clientDepositList";
        }
    }

    @PostMapping("/listOfDeposits/{id}/delete")
    public String closeDeposit(@PathVariable Long id,
                               @AuthenticationPrincipal User user,
                               Model model) {
        bankDepositServiceImpl.closeDeposit(id, user, model);
        return "deposits/clientDepositList";
    }

    @PostMapping("/listOfDeposits/{id}/put")
    public String addMoneyToDeposit(@PathVariable Long id,
                                    @AuthenticationPrincipal User user,
                                    @RequestParam(required = false) BigDecimal moneyAmount,
                                    Model model) {

        try {

            bankDepositServiceImpl.addMoneyToDeposit(id, user, moneyAmount, model);

            return "deposits/clientDepositList";

        } catch (BadBalanceException | RequestParamException e) {

            model.addAttribute("message", e.getMessage());

            return "deposits/clientDepositList";
        }
    }
}
