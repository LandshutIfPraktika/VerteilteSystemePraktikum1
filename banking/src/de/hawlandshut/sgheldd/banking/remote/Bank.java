package de.hawlandshut.sgheldd.banking.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by s-gheldd on 16.03.17.
 */
public interface Bank extends Remote {


    String JP_MORGAN = "JP_MORGAN";
    String DEUTSCHE_BANK = "DEUTSCHE_BANK";
    String ROYAL_BANK_OF_SCOTLAND = "ROYAL_BANK_OF_SCOTLAND";

    Integer openAccount(final String name, final String password) throws RemoteException;

    void deposit(final Integer accountNumber, final String name, final Integer amount) throws RemoteException, BankingException;

    void withdraw(final Integer accountNumber, final String name, final String password, final Integer amount) throws RemoteException, BankingException;

    AccountStatus accountStatus(final Integer accountNumber, final String name, final String password) throws RemoteException, BankingException;
}
