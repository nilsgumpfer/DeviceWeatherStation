package WeatherStationServer.interfaces;

import WeatherStationServer.observer.AObservable;
import de.thm.smarthome.global.beans.ActionModeBean;
import de.thm.smarthome.global.beans.ManufacturerBean;
import de.thm.smarthome.global.beans.MeasureBean;
import de.thm.smarthome.global.beans.ModelVariantBean;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Tim on 07.04.2017.
 */
public interface WeatherStationServerInterface extends Remote {

    MeasureBean getTemperature() throws RemoteException;
    MeasureBean getWindvelocity() throws RemoteException;
    MeasureBean getRainfallAmount() throws RemoteException;
    MeasureBean getAirPressure() throws RemoteException;
    MeasureBean getAirHumidity() throws RemoteException;
    ModelVariantBean getModelvariant() throws RemoteException;
    ManufacturerBean getManufacturer() throws RemoteException;
    ActionModeBean getActionMode() throws RemoteException;
    String getGenericName() throws RemoteException;
    String getSerialNumber() throws RemoteException;
    void setGenericName(String new_genericName) throws RemoteException;

    /*double getWindVelocity(WeatherStationClientInterface c) throws RemoteException;

    public double getRainfallAmount(WeatherStationClientInterface c) throws RemoteException;

    public double getAirHumidity(WeatherStationClientInterface c) throws RemoteException;

    public double getAirPressure(WeatherStationClientInterface c) throws RemoteException;

    public double getTemperature(WeatherStationClientInterface c) throws RemoteException;

    public String getName(WeatherStationClientInterface c) throws RemoteException;

    //public void update(AObservable o, Object change, WeatherStationClientInterface c);*/
}
