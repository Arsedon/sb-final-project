package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.CreditCard;
import ru.sberbank.entity.client.Passport;
import ru.sberbank.entity.client.User;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByPassport(Passport passport);

    User findByCreditCard(CreditCard creditCard);
}
