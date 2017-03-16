package de.hawlandshut.sgheldd.banking.remote;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class BankImpl extends UnicastRemoteObject implements CompliantBank {

    private final Hashtable<Integer, Account> accounts;
    private final AtomicInteger nextFreeAccountNumber;

    public BankImpl() throws RemoteException {
        super();
        nextFreeAccountNumber = new AtomicInteger(0);
        accounts = new Hashtable<>();
    }

    @Override
    public Integer openAccount(final String name, final String password) throws RemoteException {
        final Integer newNumber = nextFreeAccountNumber.getAndIncrement();
        accounts.put(newNumber, new Account(newNumber, name, password));
        //System.out.println("New Account: " + name + ", " + password + ", " + newNumber);
        return newNumber;
    }

    @Override
    public void deposit(final Integer accountNumber, final String name, final Integer amount) throws RemoteException, BankingException {
        final Account account = accounts.get(accountNumber);
        if (account == null || !account.getName().equals(name)) throw new BankingException.NoSuchAccount();
        final int newTotal;
        synchronized (account) {
            newTotal = account.getAmount() + amount;
            account.setAmount(newTotal);
        }
        //System.out.println("Deposit: " + accountNumber + ", " + name + ", " + newTotal);
    }

    @Override
    public void withdraw(final Integer accountNumber, final String name, final String password, final Integer amount) throws RemoteException, BankingException {
        final Account account = accounts.get(accountNumber);
        if (account == null || !account.getName().equals(name)) throw new BankingException.NoSuchAccount();
        if (!account.getPassword().equals(password)) throw new BankingException.IncorrectPassword();

        synchronized (account) {
            final Integer currentAmount = account.getAmount();
            if (currentAmount < amount) throw new BankingException.InsufficientFunds();
            account.setAmount(currentAmount - amount);
        }
    }

    @Override
    public AccountStatus accountStatus(final Integer accountNumber, final String name, final String password) throws RemoteException, BankingException {
        final Account account = accounts.get(accountNumber);
        if (account == null || !account.getName().equals(name)) throw new BankingException.NoSuchAccount();
        if (!account.getPassword().equals(password)) throw new BankingException.IncorrectPassword();

        return new AccountStatus(account.getAmount(), account.getAccountNumber());
    }

    @Override
    public Integer sumFor(final String name) throws RemoteException {
        return accounts.values().stream()
                .filter(a -> a.getName().equals(name))
                .mapToInt(Account::getAmount)
                .sum();
    }

    @Override
    public Collection<Integer> accountsFor(final String name) throws RemoteException {
        return accounts.values().stream()
                .filter(a -> a.getName().equals(name))
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());
    }
}
