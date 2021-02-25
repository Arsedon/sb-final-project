package ru.sberbank.controller.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.entity.client.User;
import ru.sberbank.service.RegistrationServiceImpl;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @GetMapping("/registration")
    public String registration() {
        return "main/registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String series,
                          @RequestParam String number,
                          User user,
                          Map<String, Object> model) {

        try {

            registrationService.addUser(series, number, user, model);

        } catch (IllegalArgumentException e) {

            model.put("message", e.getMessage());

            return "main/registration";

        }

        return "redirect:/login";
    }


}
