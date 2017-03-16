package de.hawlandshut.sgheldd.banking.remote;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class BankingException extends Exception {


    public static class NoSuchAccount extends BankingException {
    }

    public static class IncorrectPassword extends BankingException {
    }

    public static class InsufficientFunds extends BankingException {
    }

}