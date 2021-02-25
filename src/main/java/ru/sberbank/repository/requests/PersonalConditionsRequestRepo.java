package ru.sberbank.repository.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.entity.requests.PersonalConditionsRequest;

public interface PersonalConditionsRequestRepo extends JpaRepository<PersonalConditionsRequest, Long> {
}
