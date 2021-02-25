package ru.sberbank.entity.client;

import javax.persistence.*;

@Entity
public class Passport {

    @Id
    @GeneratedValue
    @Column(name = "passport_id")
    private Long passportId;
    private String number;
    private String series;
    @OneToOne(mappedBy = "passport", cascade = CascadeType.ALL)
    private User user;

    public Passport() {
    }

    public Long getPassportId() {
        return passportId;
    }

    public void setPassportId(Long passportId) {
        this.passportId = passportId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User client) {
        this.user = client;
    }
}
