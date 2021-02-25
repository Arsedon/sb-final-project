package ru.sberbank.exceptions;

public class BadBalanceException extends RuntimeException {

    public BadBalanceException() {
        super("Недостаточно средств!");
    }
}
