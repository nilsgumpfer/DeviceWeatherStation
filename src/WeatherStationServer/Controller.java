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
}
