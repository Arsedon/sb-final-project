package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.entity.BankAccount;
import ru.sberbank.entity.client.Passport;
import ru.sberbank.entity.client.Role;
import ru.sberbank.entity.client.User;
import ru.sberbank.repository.UserRepo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl {

    @Autowired
    private UserRepo userRepo;


    public void addUser(String series,
                        String number,
                        User user,
                        Map<String, Object> model) {

        User userFromDb = userRepo.findByUsername(user.getUsername());
        Passport passport = new Passport();
        BankAccount bankAccount = new BankAccount();

        if (userFromDb != null) {

            model.put("message", "Пользователь с таким именем уже существует!");

        }

        passport.setSeries(series);
        passport.setNumber(number);
        bankAccount.setMoney(BigDecimal.valueOf(0));
        bankAccount.setNumber(series + number);
        user.setPassport(passport);
        user.setActive(true);
        user.setBankAccount(bankAccount);
        user.setRoles(Collections.singleton(Role.USER));

        registrationUserChecking(user);

        userRepo.save(user);


    }

    private void registrationUserChecking(User user) {

        if (user.getPassport().getNumber().length() != 6) {
            throw new IllegalArgumentException("Номер паспорта должен иметь 6 знаков!");
        }

        if (user.getPassport().getSeries().length() != 4) {
            throw new IllegalArgumentException("Серия паспорта должна иметь 4 знака!");
        }

        if (user.getUsername().length() < 4) {
            throw new IllegalArgumentException("Имя пользователя должно быть больше 3 знаков");
        }

        if (user.getPassword().length() < 4) {
            throw new IllegalArgumentException("Пароль должен быть больше 3 знаков");
        }

        if (user.getFirstName().length() < 3) {
            throw new IllegalArgumentException("Имя должно быть больше 2 знаков");
        }

        if (user.getLastName().length() < 3) {
            throw new IllegalArgumentException("Фамилия должна быть больше 2 знаков");
        }

        if (user.getPatronymic().length() < 3) {
            throw new IllegalArgumentException("Отчество должно быть больше 4 знаков");
        }
    }

}
