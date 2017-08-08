package WeatherStationServer;

import WeatherStationServer.interfaces.WeatherStationServerInterface;
import de.thm.smarthome.global.observer.AObservable;
import de.thm.smarthome.global.observer.IObserver;
import de.thm.smarthome.global.beans.ActionModeBean;
import de.thm.smarthome.global.beans.ManufacturerBean;
import de.thm.smarthome.global.beans.MeasureBean;
import de.thm.smarthome.global.beans.ModelVariantBean;
import de.thm.smarthome.global.enumeration.EUnitOfMeasurement;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Tim on 07.04.2017.
 */
public class WeatherStation extends AObservable implements IObserver, WeatherStationServerInterface {

    public String weatherstationname = "SmartHomeAPI";
    public String serverstatus = null;
    public int serverport = 1099;
    public Registry rmiRegistry;
    public String serverIP;
    private WeatherStationServerInterface stub = null;

    private MeasureBean temperature = new MeasureBean(0.00, EUnitOfMeasurement.TEMPERATURE_DEGREESCELSIUS);
    private MeasureBean windvelocity = new MeasureBean(0.00, EUnitOfMeasurement.VELOCITY_KILOMETERSPERHOUR);
    private MeasureBean rainfallAmount = new MeasureBean(0.00, EUnitOfMeasurement.VOLUME_LITRESPERSQUAREMETER);
    private MeasureBean airPressure = new MeasureBean(0.00, EUnitOfMeasurement.PRESSURE_BAR);
    private MeasureBean airHumidity = new MeasureBean(20.00, EUnitOfMeasurement.RELATION_PERCENT);
    private ModelVariantBean modelvariant;
    private ManufacturerBean manufacturer;
    private ActionModeBean actionMode;
    private String genericName;
    private String serialNumber;

    public StringProperty airhumidty_sp = new SimpleStringProperty(String.valueOf(airHumidity.getMeasure_Double())+ " " + airHumidity.getUnitOfMeasurement_String());
    public StringProperty rainfallamount_sp = new SimpleStringProperty(String.valueOf(rainfallAmount.getMeasure_Double())+ " " + rainfallAmount.getUnitOfMeasurement_String());
    public StringProperty windvelocity_sp = new SimpleStringProperty(String.valueOf(windvelocity.getMeasure_Double())+ " " + windvelocity.getUnitOfMeasurement_String());
    public StringProperty airpressure_sp = new SimpleStringProperty(String.valueOf(airPressure.getMeasure_Double())+ " " + airPressure.getUnitOfMeasurement_String());
    public StringProperty temperature_sp = new SimpleStringProperty(String.valueOf(temperature.getMeasure_Double())+ " " + temperature.getUnitOfMeasurement_String());


    public WeatherStation() {

    }




    public String startServer() throws RemoteException { //TODO: RemoteExceotion fangen (bei allen Geräten!)
        serverIP = getServerIP();
        System.setProperty("java.rmi.server.hostname", serverIP);
        if(stub == null) {
            stub = (WeatherStationServerInterface) UnicastRemoteObject.exportObject(this, 0);
        }
        rmiRegistry = LocateRegistry.createRegistry(serverport);
        try {

            /*Aktiviert und definiert das Logging des Servers*/
            RemoteServer.setLog(System.out);

            /*Bindet den Server an die folgende Adresse*/
            Naming.rebind("//127.0.0.1/" + weatherstationname, this);
            this.serverstatus = "Gestartet";
            return "Server ist gestartet!";


        } catch (MalformedURLException e) {

            System.out.print(e.toString());
            return "Fehler beim Starten des Servers!";
        }
    }


    @Override
    public void update(Object o, Object change) {

    }

    public String getServerIP() {
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();

        } catch (UnknownHostException e) {

            System.out.print(e.toString());
            return null;
        }
    }

    public String stopServer(){
        try {

            rmiRegistry.unbind(weatherstationname);

            UnicastRemoteObject.unexportObject(rmiRegistry, true);
            this.serverstatus = "Gestoppt";
            return "Server ist gestoppt!";

        } catch (NoSuchObjectException e)
        {
            System.out.print(e.toString());
            return "Fehler beim Stoppen des Servers!";
        } catch (NotBoundException e)
        {
            System.out.print(e.toString());
            return "Fehler beim Stoppen des Servers!";
        } catch (RemoteException e) {
            System.out.print(e.toString());
            return "Fehler beim Stoppen des Servers!";
        }
    }

    /*Getter*/


    @Override
    public MeasureBean getTemperature() throws RemoteException {
        return temperature;
    }

    @Override
    public MeasureBean getWindvelocity() throws RemoteException {
        return windvelocity;
    }

    @Override
    public MeasureBean getRainfallAmount() throws RemoteException {
        return rainfallAmount;
    }

    @Override
    public MeasureBean getAirPressure() throws RemoteException {
        return airPressure;
    }

    @Override
    public MeasureBean getAirHumidity() throws RemoteException {
        return airHumidity;
    }

    @Override
    public ModelVariantBean getModelvariant() throws RemoteException {
        return modelvariant;
    }

    @Override
    public ManufacturerBean getManufacturer() throws RemoteException {
        return manufacturer;
    }

    @Override
    public ActionModeBean getActionMode() throws RemoteException {
        return actionMode;
    }

    @Override
    public String getGenericName() throws RemoteException {
        return genericName;
    }

    @Override
    public String getSerialNumber() throws RemoteException {
        return serialNumber;
    }

    /*Setter*/


    public void setGenericName(String new_genericName) throws RemoteException {
        genericName = new_genericName;
    }

    public void setTemperature(MeasureBean newTemp) {
        temperature = newTemp;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                temperature_sp.set(String.valueOf(newTemp.getMeasure_Double()) + " " + newTemp.getUnitOfMeasurement_String());
            }
        });
        notifyObservers(temperature);
    }

    public void setWindvelocity(MeasureBean newWindvelocity) {
        windvelocity = newWindvelocity;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                windvelocity_sp.set(String.valueOf(newWindvelocity.getMeasure_Double()) + " " + newWindvelocity.getUnitOfMeasurement_String());
            }
        });
        notifyObservers(windvelocity);
    }

    public void setRainfallAmount(MeasureBean newRainfallamount) {
        rainfallAmount = newRainfallamount;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rainfallamount_sp.set(String.valueOf(newRainfallamount.getMeasure_Double()) + " " + newRainfallamount.getUnitOfMeasurement_String());
            }
        });
        notifyObservers(rainfallAmount);
    }

    public void setAirPressure(MeasureBean newAirpressure) {
        airPressure = newAirpressure;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                airpressure_sp.set(String.valueOf(newAirpressure.getMeasure_Double()) + " " + newAirpressure.getUnitOfMeasurement_String());
            }
        });
        notifyObservers(airPressure);
    }

    public void setAirHumidity(MeasureBean newAirhumidity) {
        airHumidity = newAirhumidity;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                airhumidty_sp.set(String.valueOf(newAirhumidity.getMeasure_Double()) + " " + newAirhumidity.getUnitOfMeasurement_String());
            }
        });
        notifyObservers(airHumidity);
    }
}
