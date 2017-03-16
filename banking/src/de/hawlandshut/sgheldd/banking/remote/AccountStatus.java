package de.hawlandshut.sgheldd.banking.remote;

import java.io.Serializable;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class AccountStatus implements Serializable {
    private final Integer amount;
    private final Integer accountNumber;

    public AccountStatus(final Integer amount,final Integer accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "AccountStatus{" +
                "amount=" + amount +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
