package ru.sberbank.entity;

import ru.sberbank.entity.client.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String pinCode;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Credit> credits;
    private LocalDate validUntil = LocalDate.ofYearDay(2030, 184);
    @OneToOne
    private User client;
    private BigDecimal limitOfCredits = BigDecimal.valueOf(0);
    private boolean isActive = true;

    public CreditCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        String finalNumber = "42765400" + number;
        this.number = finalNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Set<Credit> getCredits() {
        return credits;
    }

    public void setCredits(Set<Credit> credits) {
        this.credits = credits;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public BigDecimal getLimitOfCredits() {
        return limitOfCredits;
    }

    public void setLimitOfCredits(BigDecimal limitOfCredits) {
        this.limitOfCredits = limitOfCredits;
    }

    public String getPrefixNumberOfCard() {
        String number = this.getNumber();
        char[] charNumber = number.toCharArray();
        char[] prefix = Arrays.copyOfRange(charNumber, 0, 7);
        return prefix.toString();
    }

    public void addCredit(Credit credit) {
        this.credits.add(credit);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number) &&
                Objects.equals(pinCode, that.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, pinCode);
    }

}
