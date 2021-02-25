package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.sberbank.entity.bankdeposit.BankDeposit;
import ru.sberbank.entity.client.User;

import java.util.List;

public interface BankDepositRepo extends JpaRepository<BankDeposit, Long> {

    @Modifying
    @Query("UPDATE BankDeposit SET client.id = null WHERE id = :id")
    void updateDeposit(Long id);

    List<BankDeposit> findByClient(User client);

}
