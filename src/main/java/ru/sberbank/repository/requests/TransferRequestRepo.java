package ru.sberbank.repository.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.client.User;
import ru.sberbank.entity.requests.TransferRequest;

import java.util.List;

public interface TransferRequestRepo extends JpaRepository<TransferRequest, Long> {

    List<TransferRequest> findByClient(User client);
}
