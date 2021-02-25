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
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.service.TransferServiceImpl;

import java.math.BigDecimal;

@Controller
public class TransferController {

    @Autowired
    private TransferServiceImpl transferServiceImpl;

    @GetMapping("/trReq")
    public String requestList(Model model) {
        transferServiceImpl.requestList(model);
        return "transfer/transferRequestsList";
    }

    @PostMapping("/trans")
    public String createTransferRequest(@AuthenticationPrincipal User user,
                                        @RequestParam(required = false) BigDecimal moneyAmount,
                                        @RequestParam(required = false) String cardNumber,
                                        Model model) {
        model.addAttribute("moneyAmount", moneyAmount);
        model.addAttribute("cardNumber", cardNumber);
        try {
            transferServiceImpl.createTransferRequest(user, moneyAmount, cardNumber, model);
        } catch (NullPointerException e) {

            model.addAttribute("message", "Такой кредитной карты не существует, проверьте правильность написания номера карты!");

            return "main/main";

        } catch (RequestParamException e) {

            model.addAttribute("message", e.getMessage());

            return "main/main";
        }

        return "main/main";

    }

    @PostMapping("/trReq/{id}/delete")
    public String deleteRequest(@PathVariable("id") Long id, Model model) {
        transferServiceImpl.deleteRequest(id, model);
        return "redirect:/trReq";
    }

    @PostMapping("/trReq/{id}/add")

    public String transferCreating(@PathVariable("id") String id, Model model) {
        Long newId = Long.parseLong(id.replace("\u00a0", ""));
        transferServiceImpl.addTransferToClient(newId, model);
        return "redirect:/trReq";
    }

    @GetMapping("/transfersHistory")
    public String transferHistory(@AuthenticationPrincipal User user,
                                  Model model) {
        transferServiceImpl.transferHistory(user, model);

        return "transfer/transfersList";
    }
}
