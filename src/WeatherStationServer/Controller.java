package WeatherStationServer;

import de.thm.smarthome.global.beans.MeasureBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Optional;


public class Controller {



    @FXML
    private Label lbl_Serverip;
    @FXML
    private Label lbl_Servername;
    @FXML
    private Label lbl_Serverport;
    @FXML
    private Label lbl_Serverstatus;
    @FXML
    private Label lbl_srvmsg;
    @FXML
    private Button btn_starteServer;
    @FXML
    private Button btn_stoppeServer;
    @FXML
    private TextArea ta_srvlog;
    @FXML
    private Button btn_setairhumidity;
    @FXML
    private Button btn_setRainfallamount;
    @FXML
    private Button btn_setWindvelocity;
    @FXML
    private Button btn_setAirpressure;
    @FXML
    private Button btn_setTemp;

    @FXML
    private Label lbl_ah;
    @FXML
    private Label lbl_rfa;
    @FXML
    private Label lbl_wind;
    @FXML
    private Label lbl_airp;
    @FXML
    private Label lbl_temp;

    public static PrintStream ps;

    public WeatherStation weather1 = null;



    public void BTNServerStarten(ActionEvent event) throws IOException {
        /*TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name des Shutter definieren");
        dialog.setHeaderText("Shutter anlegen");
        dialog.setContentText("Bitte diesem Shutter einen Namen geben:");

        if(!(lbl_Servername.getText().equals("-"))){

        }
        else{
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent() == true && !result.get().equals("")){
            lbl_Servername.setText(result.get());}
            else{
                return;
            }
        }*/

        if(weather1 == null){
            weather1 = new WeatherStation();
        }
        /*else{
            weather1 = new WeatherStation();
        }*/

        ps = new PrintStream(new OutputStream() {

            @Override
            public void write(int i) throws IOException {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ta_srvlog.appendText(String.valueOf((char) i));
                    }
                });


            }
        });
        System.setOut(ps);


        /*Server wird gestartet*/

        lbl_srvmsg.setText(weather1.startServer());
        lbl_Serverip.setText(weather1.getServerIP());
        lbl_Servername.setText(weather1.weatherstationname);
        lbl_Serverstatus.setText(weather1.serverstatus);

        lbl_ah.textProperty().bind(weather1.airhumidty_sp);
        lbl_airp.textProperty().bind(weather1.airpressure_sp);
        lbl_rfa.textProperty().bind(weather1.rainfallamount_sp);
        lbl_temp.textProperty().bind(weather1.temperature_sp);
        lbl_wind.textProperty().bind(weather1.windvelocity_sp);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(weather1.serverport);
        String srvport = sb.toString();

        lbl_Serverport.setText(srvport);

        if(lbl_Serverstatus.getText() == "Gestartet"){
            btn_starteServer.setDisable(true);
            btn_stoppeServer.setDisable(false);
        }
    }

    public void BTNServerStoppen(ActionEvent event){
        lbl_srvmsg.setText(weather1.stopServer());
        lbl_Serverstatus.setText(weather1.serverstatus);

        if (lbl_Serverstatus.getText() == "Gestoppt"){
            btn_stoppeServer.setDisable(true);
            btn_starteServer.setDisable(false);
        }
    }

    public void BTNSetTemp(ActionEvent event) throws RemoteException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gewünschte Temperatur einstellen");
        dialog.setHeaderText("Gewünschte Temperatur der Wetterstation einstellen");
        dialog.setContentText("Bitte gewünschte Temperatur der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newTemp = Double.parseDouble(result.get());
            System.out.println(newTemp);
            weather1.setTemperature(new MeasureBean(newTemp, weather1.getTemperature().getUnitOfMeasurement_Enum()));
        }
        else{
            return;
        }
    }

    public void BTNSetAirPressure(ActionEvent event) throws RemoteException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gewünschten Luftdruck einstellen");
        dialog.setHeaderText("Gewünschte Luftdruck der Wetterstation einstellen");
        dialog.setContentText("Bitte gewünschte Luftdruck der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newAirpressure = Double.parseDouble(result.get());
            weather1.setAirPressure(new MeasureBean(newAirpressure, weather1.getAirPressure().getUnitOfMeasurement_Enum()));
        }
        else{
            return;
        }
    }

    public void BTNSetAirHumidty(ActionEvent event) throws RemoteException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gewünschten Luftfeuchte einstellen");
        dialog.setHeaderText("Gewünschte Luftfeuchte der Wetterstation einstellen");
        dialog.setContentText("Bitte gewünschte Luftfeuchte der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newAirhumidty = Double.parseDouble(result.get());
            weather1.setAirHumidity(new MeasureBean(newAirhumidty, weather1.getAirHumidity().getUnitOfMeasurement_Enum()));
        }
        else{
            return;
        }
    }

    public void BTNSetRain(ActionEvent event) throws RemoteException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gewünschten Regenmenge einstellen");
        dialog.setHeaderText("Gewünschte Regenmenge der Wetterstation einstellen");
        dialog.setContentText("Bitte gewünschte Regenmenge der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newRainfallamount = Double.parseDouble(result.get());
            weather1.setRainfallAmount(new MeasureBean(newRainfallamount, weather1.getRainfallAmount().getUnitOfMeasurement_Enum()));
        }
        else{
            return;
        }
    }

    public void BTNSetWind(ActionEvent event) throws RemoteException{
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gewünschten Windgeschwindigkeit einstellen");
        dialog.setHeaderText("Gewünschte Windgeschwindigkeit der Wetterstation einstellen");
        dialog.setContentText("Bitte gewünschte Windgeschwindigkeit der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newWindvelocity = Double.parseDouble(result.get());
            weather1.setWindvelocity(new MeasureBean(newWindvelocity, weather1.getWindvelocity().getUnitOfMeasurement_Enum()));
        }
        else{
            return;
        }
    }
}
