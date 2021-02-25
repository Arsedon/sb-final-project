package ru.sberbank.repository.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sberbank.entity.requests.CreditRequest;

import java.util.List;

public interface CreditRequestRepo extends JpaRepository<CreditRequest, Long> {


    @Query(value = "SELECT * FROM credit_request " +
            "WHERE client_id = :clientid and final_sum > 0",
            nativeQuery = true)
    List<CreditRequest> findCalculatedRequestsForClient(@Param("clientid") Long clientId);

}
