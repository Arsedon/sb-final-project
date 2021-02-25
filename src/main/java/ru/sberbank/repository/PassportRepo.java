package ru.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.client.Passport;

public interface PassportRepo extends JpaRepository<Passport, Long> {

    Passport findByNumberAndSeries(String number, String series);
}
