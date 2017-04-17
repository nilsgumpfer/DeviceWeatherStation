package WeatherStationServer.interfaces;

import WeatherStationServer.observer.AObservable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Tim on 07.04.2017.
 */
public interface WeatherStationServerInterface extends Remote {

    public double getWindVelocity(WeatherStationClientInterface c) throws RemoteException;

    public double getRainfallAmount(WeatherStationClientInterface c) throws RemoteException;

    public double getAirHumidity(WeatherStationClientInterface c) throws RemoteException;

    public double getAirPressure(WeatherStationClientInterface c) throws RemoteException;

    public double getTemperature(WeatherStationClientInterface c) throws RemoteException;

    public String getName(WeatherStationClientInterface c) throws RemoteException;

    //public void update(AObservable o, Object change, WeatherStationClientInterface c);
}
