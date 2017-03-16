package de.hawlandshut.sgheldd.banking.remote;

import java.io.Serializable;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class Account implements Serializable {
    private final Integer accountNumber;
    private final String name;
    private final String password;

    private Integer amount;


    public Account(Integer accountNumber, String name, String password) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.password = password;

        this.amount = 0;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }
}
