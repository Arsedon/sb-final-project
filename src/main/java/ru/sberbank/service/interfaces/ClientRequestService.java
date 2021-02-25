package ru.sberbank.service.interfaces;

import org.springframework.ui.Model;
import ru.sberbank.entity.client.User;

public interface ClientRequestService {

    void requestList(Model model, User client);

    void deleteRequest(Long id, Model model);

    void addCreditToClient(Long id, Model model);
}
