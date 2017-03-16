package de.hawlandshut.sgheldd.banking.remote;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by s-gheldd on 16.03.17.
 */
public interface CompliantBank extends Bank {

    Integer sumFor(final String name) throws RemoteException;

    Collection<Integer> accountsFor(final String name) throws RemoteException;
}
