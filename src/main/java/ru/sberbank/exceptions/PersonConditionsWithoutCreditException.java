package ru.sberbank.exceptions;

public class PersonConditionsWithoutCreditException extends RuntimeException {

    public PersonConditionsWithoutCreditException() {

        super("Для подачи заявки такого формата необходимо иметь хотя бы одну кредитную карту!");

    }
}
