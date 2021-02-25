package ru.sberbank.entity;

import ru.sberbank.entity.client.User;
import ru.sberbank.exceptions.BadBalanceException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToOne(mappedBy = "bankAccount")
    private User client;

    private BigDecimal money;
    private String number;

    @Transient
    private String prefix = "12150000";

    public void setId(Long id) {
        Id = id;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getId() {
        return Id;
    }

    public User getClient() {
        return client;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getNumber() {
        return number;
    }

    public String getPrefix() {
        return prefix;
    }

    public void moneyTaking(BigDecimal moneyAmount) {
        if (money.compareTo(moneyAmount) >= 0) {
            this.money = this.money.subtract(moneyAmount);
        } else {
            throw new BadBalanceException();
        }
    }

    public void moneyAdding(BigDecimal moneyAmount) {
        this.money = this.money.add(moneyAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(Id, that.Id) &&
                Objects.equals(money, that.money) &&
                Objects.equals(number, that.number) &&
                Objects.equals(prefix, that.prefix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, money, number, prefix);
    }
}
