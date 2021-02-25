package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.Credit;
import ru.sberbank.entity.client.User;

import java.util.List;

public interface CreditRepo extends JpaRepository<Credit, Long> {

    List<Credit> findByClient(User user);

}
