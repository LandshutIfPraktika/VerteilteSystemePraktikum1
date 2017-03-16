package de.hawlandshut.sgheldd.banking.taxagency;

import de.hawlandshut.sgheldd.banking.remote.Bank;
import de.hawlandshut.sgheldd.banking.remote.CompliantBank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class TaxAgencyClient {
    public static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        final Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);

        final Map<String, CompliantBank> banks = new HashMap<>();
        banks.put(Bank.DEUTSCHE_BANK, (CompliantBank) registry.lookup(Bank.DEUTSCHE_BANK));
        banks.put(Bank.JP_MORGAN, (CompliantBank) registry.lookup(Bank.JP_MORGAN));
        banks.put(Bank.ROYAL_BANK_OF_SCOTLAND, (CompliantBank) registry.lookup(Bank.ROYAL_BANK_OF_SCOTLAND));

        while (true) {
            System.out.println("get total (1), get all accounts (2)");
            final int op = INPUT.nextInt();
            System.out.println("name");
            final String name = INPUT.next();
            switch (op) {
                case 1: {
                    int total = 0;
                    for (final CompliantBank bank :
                            banks.values()) {
                        total += bank.sumFor(name);
                    }
                    System.out.println("total assets: " + total);
                }
                break;
                case 2: {
                    final StringBuilder stringBuilder = new StringBuilder();
                    for (final Map.Entry<String, CompliantBank> entry :
                            banks.entrySet()) {
                        stringBuilder.append(entry.getKey()).append(":").append(entry.getValue().accountsFor(name));
                    }
                    System.out.println(stringBuilder.toString());
                }
                break;
                default: {
                    System.exit(0);
                }
            }
        }
    }
}
