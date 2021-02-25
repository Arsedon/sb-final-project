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
import ru.sberbank.exceptions.NonEmptyFromCreditsException;
import ru.sberbank.service.CreditServiceImpl;

import java.math.BigDecimal;

@Controller
public class CreditController {

    @Autowired
    private CreditServiceImpl creditServiceImpl;

    @GetMapping("/listOfCredits")
    public String requestList(@AuthenticationPrincipal User client, Model model) {
        creditServiceImpl.requestList(client, model);

        return "credit/creditList";
    }

    @PostMapping("/listOfCredits")
    public String creditRepay(@RequestParam String id,
                              @RequestParam BigDecimal payment,
                              Model model) {
        Long newId = Long.parseLong(id.replace("\u00a0", ""));
        try {

            creditServiceImpl.creditRepay(newId, model, payment);
        } catch (BadBalanceException e) {
            model.addAttribute("message", e.getMessage());

            return "credit/creditList";
        }

        return "redirect:/listOfCredits";
    }

    @PostMapping("/listOfCredits/{id}/delete")
    public String creditCardClose(@AuthenticationPrincipal User client,
                                  @PathVariable Long id,
                                  Model model) {
        try {

            creditServiceImpl.creditCardClose(client, id, model);

            return "credit/creditList";

        } catch (NonEmptyFromCreditsException e) {

            model.addAttribute("message", e.getMessage());

            return "credit/creditList";
        }


    }
}
