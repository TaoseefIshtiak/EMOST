package com.quanfield.emost;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author root
 */
public class WeatherInfoController implements Initializable {

    @FXML
    private Label weatherInfo;
    @FXML
    private TextField cityName;
    @FXML
    private TextArea showAppliance;;
    @FXML
    private Label gettingData;
    int tprio= 0;
    double[] finalAppUsage = new double[100];
    String city;
    StringBuilder b = new StringBuilder();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void setCity(String city){
        cityName.setText(city);
    }
    
    @FXML
    //private void showResultAction(ActionEvent event) {
    public void setTex(){    
            System.out.println("Getting Data");
            gettingData.setText("Getting Data......");
        try {
            System.out.println("No error");
            String ncity = cityName.getText();
            JSONObject motherObj  = Unirest.get("http://api.apixu.com/v1/current.json?key=ee38258d4f8d420aabd151140171307&q="+ncity).asJson().getBody().getObject();
            Double response = motherObj.getJSONObject("current").getDouble("temp_c");
            Double wind_kph = motherObj.getJSONObject("current").getDouble("wind_kph");
            Double humidity = motherObj.getJSONObject("current").getDouble("humidity");
            String condition = motherObj.getJSONObject("current").getJSONObject("condition").getString("text");
           weatherInfo.setText("Temparature = " + response+"\n"+"wind = "+wind_kph+"kph\n"+"humidity = "+humidity+"\n"+"condition = "+condition);
           System.out.println("here is weather variables "+variables.appliances[0]);
        } catch (UnirestException ex) {
            
            System.out.println(" error");
            Logger.getLogger(WeatherInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*public void setTex(String app){
        showAppliance.setText(app);
    }*/
    
   
    public void setVars(String apps[], int watts[], int numbs[], int prio[], int i, String usage){
        for(int I= 0; I<i;I++){
            System.out.println(apps[I]+" wattage "+watts[I]+" quantity "+numbs[I]+" prioprity "+prio[I]);
            tprio = tprio+prio[I];
        }
        System.out.println("you have added a total number of "+tprio+" priorities in last page");
        System.out.println(usage+" w in weather page");
        for(int I= 0; I<i;I++){
            finalAppUsage[I] = ((prio[I]*Double.parseDouble(usage))/tprio);
            String frac2 = String.format("%.2f", finalAppUsage[I]);
            finalAppUsage[I] = Double.parseDouble(frac2);
            System.out.println("all the "+apps[I]+"will consume "+ finalAppUsage[I]+" W and each of the apliance will consume "+finalAppUsage[I]/numbs[I]+" W and "+apps[I]+" is suggested to be run for "+finalAppUsage[I]/(numbs[I]*watts[I])+" hours.");
            b.append("all the "+apps[I]+" will consume "+ finalAppUsage[I]+" W and each of the apliance will consume "+finalAppUsage[I]/numbs[I]+" W and "+apps[I]+" is suggested to be run for "+ String.format("%.2f",finalAppUsage[I]/(numbs[I]*watts[I]))+" hours"+"\n");
        }
        System.out.println(b);
    }
    
     @FXML
    private void showResultAction(ActionEvent event) {
        System.out.println(b);
        showAppliance.setText(b.toString());
    
    }
    
    
    
    
    
}
