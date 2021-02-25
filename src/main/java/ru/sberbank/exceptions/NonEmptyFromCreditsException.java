package ru.sberbank.exceptions;

public class NonEmptyFromCreditsException extends RuntimeException {

    public NonEmptyFromCreditsException() {
        super("Убедитесь, что перед закрытием карты, все кредиты были погашены!");
    }

}
