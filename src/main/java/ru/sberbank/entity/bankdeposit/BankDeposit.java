package ru.sberbank.entity.bankdeposit;


import ru.sberbank.entity.client.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BankDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean isReplenish;
    private boolean isAdding;
    private boolean isEarlyClosing;
    private boolean isCapitalized;
    private boolean isRenewable;
    private BigDecimal accruedInterest = BigDecimal.valueOf(0);
    private double interestRate;
    private int term;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private User client;
    private BigDecimal puttedMoney;
    private boolean isActive;
    private LocalDate dateOfOpening;
    private LocalDate dateOfClosing;
    private LocalDate dateOfNextCalculationOfInterests;

    public BankDeposit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isReplenish() {
        return isReplenish;
    }

    public void setReplenish(boolean replenish) {
        isReplenish = replenish;
    }

    public BigDecimal getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(BigDecimal accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public BigDecimal getPuttedMoney() {
        return puttedMoney;
    }

    public void setPuttedMoney(BigDecimal puttedMoney) {
        this.puttedMoney = puttedMoney;
    }

    public boolean isAdding() {
        return isAdding;
    }

    public void setAdding(boolean adding) {
        isAdding = adding;
    }

    public boolean isEarlyClosing() {
        return isEarlyClosing;
    }

    public void setEarlyClosing(boolean earlyClosing) {
        isEarlyClosing = earlyClosing;
    }

    public boolean isCapitalized() {
        return isCapitalized;
    }

    public void setCapitalized(boolean capitalized) {
        isCapitalized = capitalized;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getDateOfOpening() {
        return dateOfOpening;
    }

    public void setDateOfOpening(LocalDate dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }

    public LocalDate getDateOfClosing() {
        return dateOfClosing;
    }

    public void setDateOfClosing(LocalDate dateOfClosing) {
        this.dateOfClosing = dateOfClosing;
    }

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
    }

    public LocalDate getDateOfNextCalculationOfInterests() {
        return dateOfNextCalculationOfInterests;
    }

    public void setDateOfNextCalculationOfInterests(LocalDate dateOfNextCalculationOfInterests) {
        this.dateOfNextCalculationOfInterests = dateOfNextCalculationOfInterests;
    }
}
