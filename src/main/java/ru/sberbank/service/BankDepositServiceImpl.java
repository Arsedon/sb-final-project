package ru.sberbank.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.sberbank.entity.BankAccount;
import ru.sberbank.entity.bankdeposit.BankDeposit;
import ru.sberbank.entity.bankdeposit.DepositRate;
import ru.sberbank.entity.client.User;
import ru.sberbank.exceptions.RequestParamException;
import ru.sberbank.repository.BankAccountRepo;
import ru.sberbank.repository.BankDepositRepo;
import ru.sberbank.repository.DepositRateRepo;
import ru.sberbank.service.interfaces.BankDepositService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BankDepositServiceImpl implements BankDepositService {

    @Autowired
    private DepositRateRepo depositRateRepo;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private BankDepositRepo bankDepositRepo;


    @Override
    public void bankDepositList(Long id, Model model) {
        model.addAttribute("depositRate", depositRateRepo.getOne(id));
    }

    @Override
    public void createBankDeposit(User client, long id, BigDecimal moneyAmount, int term) {

        DepositRate rate = depositRateRepo.getOne(id);
        client.addBankDeposit(createBankDeposit(rate, moneyAmount, term, client));

    }

    private BankDeposit createBankDeposit(DepositRate rate, BigDecimal moneyAmount, int term, User client) {

        BankDeposit bankDeposit = new BankDeposit();

        addMoneyToMainBankAccount(moneyAmount);

        client.getBankAccount().moneyTaking(moneyAmount);

        bankDeposit.setInterestRate(rate.getInterestRate());
        bankDeposit.setReplenish(rate.isReplenish());
        bankDeposit.setPuttedMoney(moneyAmount);
        bankDeposit.setEarlyClosing(rate.isEarlyClosing());
        bankDeposit.setAdding(rate.isAdding());
        bankDeposit.setCapitalized(rate.isCapitalized());
        bankDeposit.setClient(client);
        bankDeposit.setActive(true);
        bankDeposit.setDateOfOpening(LocalDate.now());
        bankDeposit.setDateOfClosing(LocalDate.now().plusMonths(term));
        bankDeposit.setDateOfNextCalculationOfInterests(bankDeposit.getDateOfOpening().plusMonths(1));

        if (term < rate.getMinTerm() || term > rate.getMaxTerm()) {
            throw new IllegalArgumentException();
        } else {
            bankDeposit.setTerm(term);
        }
        client.addBankDeposit(bankDeposit);

        bankAccountRepo.save(client.getBankAccount());
        bankDepositRepo.save(bankDeposit);

        return bankDeposit;

    }

    @Override
    public void depositRateEditForm(Model model) {
        model.addAttribute("rates", depositRateRepo.findAll());
    }

    @Override
    public void editDepositRate(Long id, String name, double interestRate, int maxTerm, int minTerm,
                                Boolean replenish, Boolean adding, Boolean earlyClosing,
                                Boolean capitalize, Boolean renewable, Model model) {
        DepositRate rate = depositRateRepo.getOne(id);

        rate.setName(name);
        rate.setInterestRate(interestRate);
        rate.setMaxTerm(maxTerm);
        rate.setMinTerm(minTerm);

        if (replenish == null) {

            rate.setReplenish(false);

        } else {

            rate.setReplenish(replenish);

        }

        if (adding == null) {

            rate.setAdding(false);

        } else {

            rate.setAdding(adding);

        }

        if (earlyClosing == null) {

            rate.setEarlyClosing(false);

        } else {

            rate.setEarlyClosing(earlyClosing);

        }

        if (capitalize == null) {

            rate.setCapitalized(false);

        } else {

            rate.setCapitalized(capitalize);

        }

        if (renewable == null) {

            rate.setRenewable(false);

        } else {

            rate.setRenewable(renewable);

        }
        depositRateRepo.save(rate);
    }

    @Override
    public void clientDepositList(User user, Model model) {
        model.addAttribute("deposits", bankDepositRepo.findByClient(user));
    }

    @Override
    public void takePercentsFromDeposit(Long id, BigDecimal moneyAmount, User user, Model model) {

        if (moneyAmount == null) {

            throw new RequestParamException();

        }

        BankDeposit clientDeposit = bankDepositRepo.getOne(id);
        if (clientDeposit.getAccruedInterest().compareTo(moneyAmount) >= 0) {

            takeMoneyFromMainBankAccount(moneyAmount);
            user.getBankAccount().moneyAdding(moneyAmount);
            clientDeposit.setAccruedInterest(clientDeposit.getAccruedInterest().subtract(moneyAmount));

            bankDepositRepo.save(clientDeposit);
            bankAccountRepo.save(user.getBankAccount());

            model.addAttribute("message", "успешное снятие процентов с остатка");
        } else {
            throw new IllegalArgumentException("Процентный остаток ниже запроценной суммы!");
        }
    }


    @Transactional
    @Override
    public void closeDeposit(Long id, User user, Model model) {

        BankDeposit clientDeposit = bankDepositRepo.getOne(id);
        clientDeposit.setActive(false);
        takeMoneyFromMainBankAccount(clientDeposit.getAccruedInterest().add(clientDeposit.getPuttedMoney()));
        user.getBankAccount().moneyAdding(clientDeposit.getPuttedMoney());
        user.getBankAccount().moneyAdding(clientDeposit.getAccruedInterest());

        bankDepositRepo.save(clientDeposit);
        bankAccountRepo.save(user.getBankAccount());

        model.addAttribute("message", "Ваш депозит успешно закрыт!");
    }

    @Transactional
    @Override
    public void addMoneyToDeposit(Long id, User user, BigDecimal moneyAmount, Model model) {

        if (moneyAmount == null) {

            throw new RequestParamException();

        }

        BankDeposit clientDeposit = bankDepositRepo.getOne(id);

        user.getBankAccount().moneyTaking(moneyAmount);
        addMoneyToMainBankAccount(moneyAmount);

        clientDeposit.setPuttedMoney(clientDeposit.getPuttedMoney().add(moneyAmount));

        bankDepositRepo.save(clientDeposit);
        bankAccountRepo.save(user.getBankAccount());

        model.addAttribute("message", "Ваш депозит поплнен!");
    }

    private void addMoneyToMainBankAccount(BigDecimal moneyAmount) {

        BankAccount mainBankAccount = bankAccountRepo.findByNumber("7777777");
        mainBankAccount.moneyAdding(moneyAmount);
        bankAccountRepo.save(mainBankAccount);
    }

    private void takeMoneyFromMainBankAccount(BigDecimal moneyAmount) {

        BankAccount mainBankAccount = bankAccountRepo.findByNumber("7777777");
        mainBankAccount.moneyTaking(moneyAmount);
        bankAccountRepo.save(mainBankAccount);
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void depositProlonging() {

        List<BankDeposit> bankDeposits = bankDepositRepo.findAll();

        List<BankDeposit> depositsNeedToBeProlong = bankDeposits.stream().
                filter((BankDeposit deposit) -> deposit.isRenewable()).
                filter((BankDeposit deposit) -> deposit.getDateOfClosing().isBefore(LocalDate.now())).
                collect(Collectors.toList());

        depositsNeedToBeProlong.forEach(d -> d.setDateOfClosing(LocalDate.now().plusMonths(1)));
        bankDepositRepo.saveAll(depositsNeedToBeProlong);

    }

    @Scheduled(cron = "0 0 12 * * *")
    public void calculateCapitalizeAccruedInterest() {

        List<BankDeposit> bankDeposits = bankDepositRepo.findAll();

        List<BankDeposit> capitalizedCalculating = bankDeposits.stream().
                filter((BankDeposit deposit) -> deposit.isCapitalized()).
                filter((BankDeposit deposit) -> deposit.getDateOfClosing().isAfter(LocalDate.now())).
                filter((BankDeposit deposit) -> deposit.getDateOfNextCalculationOfInterests().isEqual(LocalDate.now())).
                collect(Collectors.toList());

        capitalizedCalculating.forEach(bankDeposit -> {

            BigDecimal capitalizeValue = bankDeposit.getAccruedInterest().add(bankDeposit.getPuttedMoney());

            bankDeposit.setAccruedInterest(bankDeposit.getAccruedInterest().add(capitalizeValue
                    .multiply(BigDecimal.valueOf(bankDeposit.getInterestRate() / 12 / 100))));
            bankDeposit.setDateOfNextCalculationOfInterests(bankDeposit.getDateOfNextCalculationOfInterests().plusMonths(1));
        });

        bankDepositRepo.saveAll(capitalizedCalculating);

    }

    @Scheduled(cron = "0 0 12 * * *")
    public void calculateAccruedInterest() {

        List<BankDeposit> bankDeposits = bankDepositRepo.findAll();

        List<BankDeposit> calculating = bankDeposits.stream().
                filter((BankDeposit deposit) -> !deposit.isCapitalized()).
                filter((BankDeposit deposit) -> deposit.getDateOfClosing().isAfter(LocalDate.now())).
                filter((BankDeposit deposit) -> deposit.getDateOfNextCalculationOfInterests().isEqual(LocalDate.now())).
                collect(Collectors.toList());

        calculating.forEach(bankDeposit -> {
            bankDeposit.setAccruedInterest(bankDeposit.getAccruedInterest().add(bankDeposit.getPuttedMoney()
                    .multiply(BigDecimal.valueOf(bankDeposit.getInterestRate() / 12 / 100))));
            bankDeposit.setDateOfNextCalculationOfInterests(bankDeposit.getDateOfNextCalculationOfInterests().plusMonths(1));
        });

        bankDepositRepo.saveAll(calculating);
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void stopDepositWorking() {

        List<BankDeposit> bankDeposits = bankDepositRepo.findAll();

        List<BankDeposit> depositsWhichNeedToBeStopped = bankDeposits.stream().
                filter(bankDeposit -> !bankDeposit.isEarlyClosing()).
                filter(bankDeposit -> LocalDate.now().isAfter(bankDeposit.getDateOfClosing())).
                collect(Collectors.toList());

        depositsWhichNeedToBeStopped.forEach(bankDeposit -> bankDeposit.setEarlyClosing(true));

        bankDepositRepo.saveAll(depositsWhichNeedToBeStopped);
    }
}
