package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.sberbank.entity.Credit;
import ru.sberbank.entity.client.User;
import ru.sberbank.entity.requests.PersonalConditionsRequest;
import ru.sberbank.exceptions.PersonConditionsWithoutCreditException;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.repository.UserRepo;
import ru.sberbank.repository.requests.PersonalConditionsRequestRepo;
import ru.sberbank.service.interfaces.PersonalConditionsService;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PersonalConditionsServiceImpl implements PersonalConditionsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PersonalConditionsRequestRepo personalConditionsRequestRepo;

    @Override
    public void requestList(Model model) {
        model.addAttribute("personRequests", personalConditionsRequestRepo.findAll());
    }

    @Override
    public void createPersonalRequest(User user, Double bankCommission, Double penaltyValue, Model model) {

        if (bankCommission == null || penaltyValue == null) {
            throw new RequestParamException();
        }

        if (user.getCreditCard() != null) {
            PersonalConditionsRequest personalReq = new PersonalConditionsRequest();
            personalReq.setClient(user);
            personalReq.setPenaltyValue(penaltyValue);
            personalReq.setBankCommission(bankCommission);
            personalConditionsRequestRepo.save(personalReq);
            model.addAttribute("message", "Заявка создана успешно!");
        } else throw new PersonConditionsWithoutCreditException();

    }

    @Override
    public void executeRequest(Long id, Model model) {
        PersonalConditionsRequest request = personalConditionsRequestRepo.getOne(id);
        User client = request.getClient();
        client.setBankCommission(request.getBankCommission());
        Set<Credit> credits = client.getCreditCard().getCredits();
        for (Credit c : credits) {
            c.setPenaltyValue(request.getPenaltyValue());
        }
        client.getCreditCard().setCredits(credits);

        userRepo.save(client);
        personalConditionsRequestRepo.delete(request);

    }

    @Override
    public void rejectRequest(Long id, Model model) {
        PersonalConditionsRequest request = personalConditionsRequestRepo.getOne(id);
        personalConditionsRequestRepo.delete(request);
    }
}
