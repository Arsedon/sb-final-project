package ru.sberbank.service.interfaces;

import ru.sberbank.entity.client.User;

import java.util.Map;

public interface RegistrationService {

    void addUser(String series, String number, User user, Map<String, Object> model);

}
