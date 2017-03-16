package de.hawlandshut.sgheldd.banking.bank;

import de.hawlandshut.sgheldd.banking.remote.Bank;
import de.hawlandshut.sgheldd.banking.remote.BankImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by s-gheldd on 16.03.17.
 */
public class BankServer {

    private static volatile boolean keepRunning = true;

    public static void main(String[] args) throws Exception {
        final Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registerBank(Bank.JP_MORGAN, registry);
        registerBank(Bank.DEUTSCHE_BANK, registry);
        registerBank(Bank.ROYAL_BANK_OF_SCOTLAND, registry);
        System.out.println("Banks are registered");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutting down registry");

            try {
                UnicastRemoteObject.unexportObject(registry, true);
            } catch (NoSuchObjectException e) {
                e.printStackTrace();
            } finally {
                keepRunning = false;
            }
        }));

        while (keepRunning) {
            Thread.sleep(100);
        }
    }

    private static void registerBank(final String name, final Registry registry) throws AlreadyBoundException, RemoteException {
        registry.bind(name, new BankImpl());
    }
}
