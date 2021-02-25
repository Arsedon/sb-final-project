package ru.sberbank.service.interfaces;

import org.springframework.ui.Model;
import ru.sberbank.entity.client.User;

import java.math.BigDecimal;

public interface CreditService {

    void requestList(User client, Model model);

    void creditRepay(Long id, Model model, BigDecimal payment);

    void creditCardClose(User client, Long id, Model model);

}
