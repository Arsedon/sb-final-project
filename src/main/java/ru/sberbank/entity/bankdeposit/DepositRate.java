package ru.sberbank.entity.bankdeposit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class DepositRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double interestRate;
    private int maxTerm;
    private int minTerm;
    private boolean isReplenish;
    private boolean isAdding;
    private boolean isEarlyClosing;
    private boolean isCapitalized;
    private boolean isRenewable;
    private String name;


    public DepositRate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getMaxTerm() {
        return maxTerm;
    }

    public void setMaxTerm(int term) {
        this.maxTerm = term;
    }

    public boolean isReplenish() {
        return isReplenish;
    }

    public int getMinTerm() {
        return minTerm;
    }

    public void setMinTerm(int minTerm) {
        this.minTerm = minTerm;
    }

    public boolean isCapitalized() {
        return isCapitalized;
    }

    public void setCapitalized(boolean capitalized) {
        isCapitalized = capitalized;
    }

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
    }

    public String Renewable() {
        if (isReplenish == true) {
            return "Продляемый";
        } else {
            return "Без продления";
        }
    }

    public String Adding() {
        if (isReplenish == true) {
            return "Пополняемый";
        } else {
            return "Без пополнения";
        }
    }

    public String Replenish() {
        if (isReplenish == true) {
            return "Со снятием средств";
        } else {
            return "Без снятия средств";
        }
    }

    public String EarlyClosing() {
        if (isReplenish == true) {
            return "Досрочное закрытие";
        } else {
            return "Без досрочного закрытия";
        }
    }

    public String capitalized() {
        if (isReplenish == true) {
            return "Капитулизируемый";
        } else {
            return "Некапитализируемый";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReplenish(boolean replenish) {
        isReplenish = replenish;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositRate that = (DepositRate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
