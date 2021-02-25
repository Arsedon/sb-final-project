package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.bankdeposit.DepositRate;

public interface DepositRateRepo extends JpaRepository<DepositRate, Long> {


}
