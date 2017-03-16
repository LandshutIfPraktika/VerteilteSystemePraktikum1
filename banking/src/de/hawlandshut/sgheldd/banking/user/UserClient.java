package de.hawlandshut.sgheldd.banking.user;

import de.hawlandshut.sgheldd.banking.remote.Bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class UserClient {

    public static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        final Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);

        final Map<String, Bank> banks = new HashMap<>();
        banks.put(Bank.DEUTSCHE_BANK, (Bank) registry.lookup(Bank.DEUTSCHE_BANK));
        banks.put(Bank.JP_MORGAN, (Bank) registry.lookup(Bank.JP_MORGAN));
        banks.put(Bank.ROYAL_BANK_OF_SCOTLAND, (Bank) registry.lookup(Bank.ROYAL_BANK_OF_SCOTLAND));


        while (true) {
            System.out.println("Which Bank do you want to access? " + banks.keySet());
            final String bankName = INPUT.next();

            final Bank bank = banks.get(bankName);
            if (bank == null) break;

            bankOperations(bank);
        }
    }

    private static void bankOperations(final Bank bank) {
        try {
            int op = 1;
            while (op != 0) {
                System.out.println("Open account (1), deposit (2), withdraw (3), info (4)");
                op = INPUT.nextInt();
                switch (op) {
                    case 1: {
                        System.out.println("name, password");
                        final String name = INPUT.next();
                        final String password = INPUT.next();
                        final Integer accountNumber = bank.openAccount(name, password);
                        System.out.println(accountNumber);
                        break;
                    }
                    case 2: {
                        System.out.println("accountNumber, name, amount");
                        final int accountNumber = INPUT.nextInt();
                        final String name = INPUT.next();
                        final int amount = INPUT.nextInt();
                        bank.deposit(accountNumber, name, amount);
                        break;
                    }
                    case 3: {
                        System.out.println("accountNumber, name, password, amount");
                        final int accountNumber = INPUT.nextInt();
                        final String name = INPUT.next();
                        final String password = INPUT.next();
                        final int amount = INPUT.nextInt();
                        bank.withdraw(accountNumber, name, password, amount);
                    }
                    case 4: {
                        System.out.println("accountNumber, name, password");
                        final int accountNumber = INPUT.nextInt();
                        final String name = INPUT.next();
                        final String password = INPUT.next();
                        System.out.println(bank.accountStatus(accountNumber, name, password));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass());
        }
    }
}
