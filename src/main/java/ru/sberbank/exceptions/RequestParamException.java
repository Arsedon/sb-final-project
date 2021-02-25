package ru.sberbank.exceptions;

public class RequestParamException extends RuntimeException {

    public RequestParamException() {

        super("Введите все значения в полях!");

    }
}
