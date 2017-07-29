package WeatherStationServer;

import WeatherStationServer.interfaces.WeatherStationClientInterface;
import WeatherStationServer.interfaces.WeatherStationServerInterface;
import WeatherStationServer.observer.AObservable;
import WeatherStationServer.observer.IObserver;
import de.thm.smarthome.global.beans.ActionModeBean;
import de.thm.smarthome.global.beans.ManufacturerBean;
import de.thm.smarthome.global.beans.MeasureBean;
import de.thm.smarthome.global.beans.ModelVariantBean;
import de.thm.smarthome.global.enumeration.EUnitOfMeasurement;
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
        WeatherStationServerInterface stub = (WeatherStationServerInterface) UnicastRemoteObject.exportObject(this, 0);
        rmiRegistry = LocateRegistry.createRegistry(serverport);
        try {
            /*if (System.getSecurityManager() == null) {
                System.setProperty("java.security.policy", "file:C:\\Users\\Tim\\IdeaProjects\\HeizungServer\\out\\production\\HeizungServer\\HeizungServer\\server.policy");
                System.setSecurityManager(new SecurityManager());

            }*/
            /*Aktiviert und definiert das Logging des Servers*/
            RemoteServer.setLog(System.out);
            //System.out.println(srvlog.toString());
            /*Bindet den Server an die folgende Adresse*/
            Naming.rebind("//127.0.0.1/" + weatherstationname, this);
            this.serverstatus = "Gestartet";
            return "Server ist gestartet!";


        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Fehler beim Starten des Servers!";
        }
    }


    @Override
    public void update(AObservable o, Object change) {

    }

    public String getServerIP() {
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String stopServer(){
        try {

            //Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", serverport);
            //HeizungServerInterface myService = (HeizungServerInterface) rmiRegistry.lookup(heizungname);

            rmiRegistry.unbind(weatherstationname);

            //UnicastRemoteObject.unexportObject(myService, true);
            UnicastRemoteObject.unexportObject(rmiRegistry, true);
            this.serverstatus = "Gestoppt";
            return "Server ist gestoppt!";

        } catch (NoSuchObjectException e)
        {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        } catch (NotBoundException e)
        {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        }
    }

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

    @Override
    public void setGenericName(String new_genericName) throws RemoteException {
        genericName = new_genericName;
    }
}
