package ru.sberbank.controller.greeting;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.entity.Currency;
import ru.sberbank.entity.client.User;
import ru.sberbank.exceptions.PersonConditionsWithoutCreditException;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.repository.DepositRateRepo;
import ru.sberbank.service.ClientRequestServiceImpl;
import ru.sberbank.service.PersonalConditionsServiceImpl;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ClientRequestServiceImpl clientRequestService;

    @Autowired
    private DepositRateRepo depositRateRepo;

    @Autowired
    private PersonalConditionsServiceImpl personalConditionsServiceImpl;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "main/greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {

        model.addAttribute("Currencies", Currency.values());
        model.addAttribute("rates", depositRateRepo.findAll());

        return "main/main";
    }

    @PostMapping("/creditreq")
    public String createCreditRequest(@AuthenticationPrincipal User user,
                                      @RequestParam(required = false) BigDecimal sum,
                                      @RequestParam Map<String, String> form,
                                      Model model) {
        try {

            clientRequestService.createCreditRequest(user, sum, form, model);

            return "main/main";


        } catch (RequestParamException e) {

            model.addAttribute("message", e.getMessage());

            return "main/main";

        }
    }

    @PostMapping("/personReq")
    public String createPersonalRequest(@AuthenticationPrincipal User user,
                                        @RequestParam(required = false) Double bankCommission,
                                        @RequestParam(required = false) Double penaltyValue, Model model) {
        try {

            personalConditionsServiceImpl.createPersonalRequest(user, bankCommission, penaltyValue, model);

        } catch (PersonConditionsWithoutCreditException e) {

            model.addAttribute("message", e.getMessage());

            return "main/main";

        } catch (RequestParamException e) {

            model.addAttribute("message", e.getMessage());

        }

        return "main/main";

    }
}
