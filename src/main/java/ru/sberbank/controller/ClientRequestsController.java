package ru.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.entity.client.User;
import ru.sberbank.service.ClientRequestServiceImpl;

@Controller
public class ClientRequestsController {

    @Autowired
    private ClientRequestServiceImpl clientService;

    @GetMapping("/reqlist")
    public String requestList(@AuthenticationPrincipal User user,
                              Model model) {
        clientService.requestList(model, user);
        return "/request/clientRequestList";
    }

    @PostMapping("/reqlist/{id}/delete")
    public String deleteRequest(@PathVariable("id") Long id, Model model) {
        clientService.deleteRequest(id, model);
        return "redirect:/reqlist";
    }

    @PostMapping("/reqlist/{id}/add")
    public String addCreditToClient(@PathVariable("id") Long id, Model model) {
        clientService.addCreditToClient(id, model);
        return "redirect:/reqlist";
    }


}
