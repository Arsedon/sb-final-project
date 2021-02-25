package ru.sberbank.entity.requests;


import ru.sberbank.entity.CreditCard;
import ru.sberbank.entity.Currency;
import ru.sberbank.entity.client.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
public class CreditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal requestedSum;
    private BigDecimal finalSum;
    private double interestRate;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User client;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "id"))
    private CreditCard cardOfClient;

    @ElementCollection(targetClass = Currency.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "credit_req_currency", joinColumns = @JoinColumn(name = "req_id"))
    @Enumerated(EnumType.STRING)
    private Set<Currency> currency;

    private double penaltyValue = 1;


    public CreditRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public CreditCard getCardOfClient() {
        return cardOfClient;
    }

    public void setCardOfClient(CreditCard cardOfClient) {
        this.cardOfClient = cardOfClient;
    }

    public Set<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(Set<Currency> currency) {
        this.currency = currency;
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
        CreditRequest that = (CreditRequest) o;
        return Double.compare(that.interestRate, interestRate) == 0 &&
                Double.compare(that.penaltyValue, penaltyValue) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(requestedSum, that.requestedSum) &&
                Objects.equals(finalSum, that.finalSum) &&
                Objects.equals(client, that.client) &&
                Objects.equals(cardOfClient, that.cardOfClient) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestedSum, finalSum, interestRate, client, cardOfClient, currency, penaltyValue);
    }

    @Override
    public String toString() {
        return "CreditRequest{" +
                "id=" + id +
                ", requestedSum=" + requestedSum +
                ", finalSum=" + finalSum +
                ", interestRate=" + interestRate +
                ", client=" + client +
                ", cardOfClient=" + cardOfClient +
                ", currency=" + currency +
                ", penaltyValue=" + penaltyValue +
                '}';
    }
}

