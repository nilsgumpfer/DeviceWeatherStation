package WeatherStationServer;

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
    private Button btn_getInfo;
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
        TextInputDialog dialog = new TextInputDialog();
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
        }

        if(weather1 == null){
            weather1 = new WeatherStation();
        }
        else{
            weather1 = new WeatherStation();
        }

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

        lbl_srvmsg.setText(weather1.startServer(lbl_Servername.getText()));
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

    public void BTNGetInfo(ActionEvent event){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Aktuelle Daten der Wetterstation");

            String aktTemp = String.valueOf(weather1.getTemperatureSrv());
            String akthumidty = String.valueOf(weather1.getAirHumiditySrv());
            String aktrainfallamount = String.valueOf(weather1.getRainfallAmountSrv());
            String aktWindvelocity = String.valueOf(weather1.getWindVelocitySrv());
            String aktAirpressure = String.valueOf(weather1.getAirPressureSrv());

            alert.setContentText("Temperatur: " + aktTemp + " °C" + "\n" + "Luftfeuchte: " + akthumidty + " %" + "\n" +"Niederschlagsmenge: " + aktrainfallamount + " l" + "\n" +"Windgeschwindigkeit: " + aktWindvelocity
            + " km/h" +"\n" + "Luftdruck: " + aktAirpressure + " hPa");

            alert.showAndWait();
        }

    public void BTNSetTemp(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Temperatur einstellen");
        dialog.setHeaderText("Temperatur der Wetterstation einstellen");
        dialog.setContentText("Bitte Temperatur der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newTemp = Double.parseDouble(result.get());
            weather1.setTemperatureSrv(newTemp);
        }
        else{
            return;
        }
    }

    public void BTNSetAirHumidity(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Luftfeuchtigkeit einstellen");
        dialog.setHeaderText("Luftfeuchtigkeit der Wetterstation einstellen");
        dialog.setContentText("Bitte Luftfeuchtigkeit der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newAirHumidity = Double.parseDouble(result.get());
            weather1.setAirHumiditySrv(newAirHumidity);
        }
        else{
            return;
        }
    }

    public void BTNSetAirPressure(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Luftdruck einstellen");
        dialog.setHeaderText("Luftdruck der Wetterstation einstellen");
        dialog.setContentText("Bitte Luftdruck der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newAirPressure = Double.parseDouble(result.get());
            weather1.setAirPressureSrv(newAirPressure);
        }
        else{
            return;
        }
    }

    public void BTNSetRainfall(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Niederschlagsmenge einstellen");
        dialog.setHeaderText("Niederschlagsmenge der Wetterstation einstellen");
        dialog.setContentText("Bitte Niederschlagsmenge der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newRainfallAmount = Double.parseDouble(result.get());
            weather1.setRainfallAmountSrv(newRainfallAmount);
        }
        else{
            return;
        }
    }

    public void BTNSetWindvelocity(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Windgeschwindigkeit einstellen");
        dialog.setHeaderText("Windgeschwindigkeit der Wetterstation einstellen");
        dialog.setContentText("Bitte Windgeschwindigkeit der Wetterstation einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newWindvelocity = Double.parseDouble(result.get());
            weather1.setWindVelocitySrv(newWindvelocity);
        }
        else{
            return;
        }
    }
}
