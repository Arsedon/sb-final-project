package ru.sberbank.service.fraudmonitor;

import org.springframework.stereotype.Component;
import ru.sberbank.entity.requests.TransferRequest;

import java.math.BigDecimal;

@Component
public class FraudMonitor {

    public boolean checkTransfer(TransferRequest transferRequest) {
        if (transferRequest.getMoneyAmount().compareTo(BigDecimal.valueOf(15000)) == -1) {
            return true;
        }
        return false;
    }
}
