package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sberbank.entity.Transfer;
import ru.sberbank.entity.client.User;

import java.util.List;

public interface TransferRepo extends JpaRepository<Transfer, Long> {

    @Query(value = "SELECT * FROM transfer " +
            "WHERE is_active = false",
            nativeQuery = true)
    List<Transfer> findNonActiveTransfers();

    @Query(value = "SELECT t.id, t.card_number, t.money_amount  FROM transfer t " +
            "JOIN usr_transfers ut ON t.id = ut.transfers_id " +
            "WHERE ut.user_id " +
            "IN (SELECT id FROM usr  + WHERE id = :clientId)", nativeQuery = true)
    List<Transfer> findByClientId(@Param("clientId") long id);

    List<Transfer> findByClient(User user);

    void deleteByClient(User user);


}
