package ru.sberbank.entity;

import ru.sberbank.entity.client.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User client;
    private BigDecimal requestedSum;
    private BigDecimal finalSum;
    private double interestRate;
    private LocalDate dateOfTakingCredit;
    private LocalDate maturity;
    @ManyToOne
    private CreditCard creditCard;
    private double penaltyValue = 1;

    public Credit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public BigDecimal getRequestedSum() {
        return requestedSum;
    }

    public void setRequestedSum(BigDecimal requestedSum) {
        this.requestedSum = requestedSum;
    }

    public BigDecimal getFinalSum() {
        return finalSum;
    }

    public void setFinalSum(BigDecimal finalSum) {
        this.finalSum = finalSum;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getDateOfTakingCredit() {
        return dateOfTakingCredit;
    }

    public void setDateOfTakingCredit(LocalDate dateOfTakingCredit) {
        this.dateOfTakingCredit = dateOfTakingCredit;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public void setMaturity(LocalDate maturity) {
        this.maturity = maturity;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public double getPenaltyValue() {
        return penaltyValue;
    }

    public void setPenaltyValue(double penaltyValue) {
        this.penaltyValue = penaltyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Double.compare(credit.interestRate, interestRate) == 0 &&
                Objects.equals(id, credit.id) &&
                Objects.equals(client, credit.client) &&
                Objects.equals(requestedSum, credit.requestedSum) &&
                Objects.equals(finalSum, credit.finalSum) &&
                Objects.equals(dateOfTakingCredit, credit.dateOfTakingCredit) &&
                Objects.equals(maturity, credit.maturity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, requestedSum, finalSum, interestRate, dateOfTakingCredit, maturity);
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", client=" + client +
                ", requestedSum=" + requestedSum +
                ", finalSum=" + finalSum +
                ", interestRate=" + interestRate +
                '}';
    }
}
