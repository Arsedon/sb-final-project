package ru.sberbank.exceptions;

public class ExceedingLimitException extends RuntimeException {

    public ExceedingLimitException() {
        super("Превышен лимит взятия кредитов");
    }

}
