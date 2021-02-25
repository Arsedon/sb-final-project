package ru.sberbank.service.interfaces;

import org.springframework.ui.Model;
import ru.sberbank.entity.client.User;

public interface PersonalConditionsService {

    void requestList(Model model);

    void createPersonalRequest(User user, Double bankCommission, Double penaltyValue, Model model);

    void executeRequest(Long id, Model model);

    void rejectRequest(Long id, Model model);
}
