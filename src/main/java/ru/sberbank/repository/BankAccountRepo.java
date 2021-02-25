package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.BankAccount;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {

    BankAccount findByNumber(String number);
}
