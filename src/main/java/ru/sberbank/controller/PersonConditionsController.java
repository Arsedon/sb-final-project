package ru.sberbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.service.PersonalConditionsServiceImpl;

@Controller
public class PersonConditionsController {

    @Autowired
    private PersonalConditionsServiceImpl personalConditionsServiceImpl;

    @GetMapping("/personConditionsList")
    public String requestList(Model model) {
        personalConditionsServiceImpl.requestList(model);
        return "personconditions/conditionsRequestList";
    }

    @PostMapping("/personConditionsList/{id}/add")
    public String executeRequest(@PathVariable("id") String id, Model model) {
        Long newId = Long.parseLong(id.replace("\u00a0", ""));
        personalConditionsServiceImpl.executeRequest(newId, model);
        return "redirect:/personConditionsList";
    }

    @PostMapping("/personConditionsList/{id}/delete")
    public String rejectRequest(@PathVariable("id") String id, Model model) {
        Long newId = Long.parseLong(id.replace("\u00a0", ""));
        personalConditionsServiceImpl.rejectRequest(newId, model);
        return "redirect:/personConditionsList";
    }

}
