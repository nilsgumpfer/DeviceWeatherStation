package WeatherStationServer;

import WeatherStationServer.interfaces.WeatherStationClientInterface;
import WeatherStationServer.interfaces.WeatherStationServerInterface;
import WeatherStationServer.observer.AObservable;
import WeatherStationServer.observer.IObserver;

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

    public String weatherstationname = null;
    public String serverstatus = null;
    public int serverport = 1099;
    public Registry rmiRegistry;
    private double airhumidity = 60.0;
    private double rainfallamount = 10.0;
    private double windvelocity = 5.0;
    private double airpressure = 938.1;
    private double temperature = 25.0;

    public WeatherStation() {

    }

    public double getWindVelocity(WeatherStationClientInterface c) {

        return windvelocity;
    }

    public double getRainfallAmount(WeatherStationClientInterface c) {

        return rainfallamount;
    }

    public double getAirHumidity(WeatherStationClientInterface c) {

        return airhumidity;
    }

    public double getAirPressure(WeatherStationClientInterface c) {

        return airpressure;
    }

    public double getTemperature(WeatherStationClientInterface c) {
        return temperature;
    }

    @Override
    public String getName(WeatherStationClientInterface c) {
        return weatherstationname;
    }

    /*Methoden für Server*/

    public double getWindVelocitySrv() {

        return windvelocity;
    }

    public double getRainfallAmountSrv() {

        return rainfallamount;
    }

    public double getAirHumiditySrv() {

        return airhumidity;
    }

    public double getAirPressureSrv() {

        return airpressure;
    }

    public double getTemperatureSrv() {
        return temperature;
    }

    public String getNameSrv() {
        return weatherstationname;
    }

    /*Setter Server*/
    public void setWindVelocitySrv(double newWindvelocity) {

        windvelocity = newWindvelocity;
    }

    public void setRainfallAmountSrv(double newRainfallamount) {

        rainfallamount = newRainfallamount;
    }

    public void setAirHumiditySrv(double newAirHumidty) {

        airhumidity = newAirHumidty;
    }

    public void setAirPressureSrv(double newAirPressure) {

        airpressure = newAirPressure;
    }

    public void setTemperatureSrv(double newTemp) {
        temperature = newTemp;
    }


    public String startServer(String wetterstationname) throws RemoteException {
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
            Naming.rebind("//127.0.0.1/" + wetterstationname, this);
            this.weatherstationname = wetterstationname;
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
}
