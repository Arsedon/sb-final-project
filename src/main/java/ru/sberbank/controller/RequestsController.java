package ru.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.entity.requests.CreditRequest;
import ru.sberbank.exceptions.ExceedingLimitException;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.service.RequestServiceImpl;

import java.math.BigDecimal;

@Controller
@RequestMapping("/request")
@PreAuthorize("hasAuthority('MANAGER')")
public class RequestsController {

    @Autowired
    private RequestServiceImpl requestServiceImpl;

    @GetMapping
    public String requestList(Model model) {
        requestServiceImpl.requestList(model);
        return "/request/requestList";
    }

    @GetMapping("{request}")
    public String requestEditForm(@PathVariable("request") Long id, Model model) {
        requestServiceImpl.requestEditForm(id, model);
        return "/request/requestEdit";
    }

    @PostMapping
    public String requestSave(
            @RequestParam(required = false) Double interestRate,
            @RequestParam("id") CreditRequest request,
            Model model) throws ExceedingLimitException {
        try {
            requestServiceImpl.requestSave(interestRate, request);
            return "redirect:/request";

        } catch (ExceedingLimitException e) {

            model.addAttribute("message", e.getMessage());
            return "redirect:/request";

        } catch (RequestParamException e) {

            model.addAttribute("message", e.getMessage());
            return "request/requestEdit";

        }
    }

    @GetMapping("{request}/cardcreate")
    public String cardCreateForm(@PathVariable String request) {
        return "card/cardCreate";
    }


    @PostMapping("{request}/cardcreate")
    public String creditCardCreate(@PathVariable CreditRequest request,
                                   @RequestParam String number,
                                   @RequestParam String pinCode,
                                   @RequestParam(required = false) BigDecimal limitOfCredits,
                                   Model model) {
        try {
            requestServiceImpl.creditCardCreate(request, number, pinCode, limitOfCredits, model);

            model.addAttribute("request", request.getId());
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "card/cardCreate";
        }
        return "redirect:/request";
    }
}
