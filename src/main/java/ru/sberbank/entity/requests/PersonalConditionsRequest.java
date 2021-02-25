package ru.sberbank.entity.requests;

import ru.sberbank.entity.client.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PersonalConditionsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User client;
    private double penaltyValue = 1;
    private double bankCommission = 10;

    public PersonalConditionsRequest() {
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

    public double getPenaltyValue() {
        return penaltyValue;
    }

    public void setPenaltyValue(double penaltyValue) {
        this.penaltyValue = penaltyValue;
    }

    public double getBankCommission() {
        return bankCommission;
    }

    public void setBankCommission(double bankCommission) {
        this.bankCommission = bankCommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalConditionsRequest that = (PersonalConditionsRequest) o;
        return Double.compare(that.penaltyValue, penaltyValue) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, penaltyValue);
    }
}
