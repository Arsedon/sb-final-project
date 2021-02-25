package ru.sberbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.sberbank.entity.CreditCard;
import ru.sberbank.entity.Transfer;
import ru.sberbank.entity.client.User;
import ru.sberbank.entity.requests.TransferRequest;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.repository.CreditCardRepo;
import ru.sberbank.repository.TransferRepo;
import ru.sberbank.repository.UserRepo;
import ru.sberbank.repository.requests.TransferRequestRepo;
import ru.sberbank.service.fraudmonitor.FraudMonitor;
import ru.sberbank.service.interfaces.TransferService;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransferRequestRepo transferRequestRepo;

    @Autowired
    private CreditCardRepo creditCardRepo;

    @Autowired
    private TransferRepo transferRepo;

    @Autowired
    private FraudMonitor fraudMonitor;

    public void requestList(Model model) {

        model.addAttribute("transferRequest", transferRequestRepo.findAll());

    }


    @Transactional
    @Override
    public void createTransferRequest(User user, BigDecimal moneyAmount, String cardNumber, Model model) {

        if (moneyAmount == null || cardNumber == null) {
            throw new RequestParamException();
        }

        CreditCard cardForTransfer = creditCardRepo.findByNumber(cardNumber);
        User userForTransfer = cardForTransfer.getClient();
        TransferRequest request = requestCreate(user, moneyAmount, cardNumber);

        if (fraudMonitor.checkTransfer(request)) {

            defaultAddingTransfer(user, userForTransfer, request, moneyAmount);
            model.addAttribute("message", "Перевод выполнен успешно");

        } else {

            model.addAttribute("message", "Подозрительный перевод.Ожидайте подтверждения перевода от менеджера!");
            transferRequestRepo.save(request);

        }
    }

    @Transactional
    @Override
    public void addTransferToClient(Long id, Model model) {

        TransferRequest request = transferRequestRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        User userForTransfer = creditCardRepo.findByNumber(request.getCardNumber()).getClient();
        User user = request.getClient();

        defaultAddingTransfer(user, userForTransfer, request, request.getMoneyAmount());

        model.addAttribute("addedRequest", request);
        transferRequestRepo.delete(request);

    }

    @Override
    public void deleteRequest(Long id, Model model) {

        TransferRequest transferRequest = transferRequestRepo.getOne(id);
        transferRequestRepo.delete(transferRequest);

    }


    private void defaultAddingTransfer(User client, User clientForTransfer, TransferRequest request, BigDecimal moneyAmount) {

        client.addTransfer(transferCreating(request));

        clientForTransfer.getBankAccount().moneyAdding(moneyAmount);

        if (checkBankAffiliation(clientForTransfer.getCreditCard())) {

            client.getBankAccount().moneyTaking(moneyAmount);

        } else {

            client.getBankAccount().moneyTaking(moneyAmount.multiply(BigDecimal.valueOf(1.1)));

        }

        userRepo.save(client);

    }

    @Override
    public void transferHistory(User user, Model model) {
        model.addAttribute("transfers", transferRepo.findByClient(user));
    }

    private TransferRequest requestCreate(User user, BigDecimal moneyAmount, String cardNumber) {

        TransferRequest request = new TransferRequest();
        request.setCardNumber(cardNumber);
        request.setClient(user);
        request.setMoneyAmount(moneyAmount);

        return request;
    }

    private Transfer transferCreating(TransferRequest request) {

        Transfer transfer = new Transfer();
        transfer.setCardNumber(request.getCardNumber());
        transfer.setClient(request.getClient());
        transfer.setMoneyAmount(request.getMoneyAmount());
        transferRepo.save(transfer);

        return transfer;
    }

    private boolean checkBankAffiliation(CreditCard creditCard) {

        if (creditCard.getPrefixNumberOfCard().equals("42765400")) {

            return true;

        }

        return false;
    }


}
