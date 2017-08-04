package de.thm.smarthome.global.observer;

import java.rmi.RemoteException;

/**
 * Created by Nils on 28.01.2017.
 */
public interface IObserver {
    public void update(Object o, Object change) throws RemoteException;
}
