package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.CreditCard;
import ru.sberbank.entity.client.User;

public interface CreditCardRepo extends JpaRepository<CreditCard, Long> {

    CreditCard findByNumber(String number);

    CreditCard findByClient(User client);

    void deleteByClient(User client);
}
