package com.quanfield.emost;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    @FXML
    private TextField appliancesWattage;
    @FXML
    private TextField appliancesNumber;

    @FXML
    private TextField lastDayUsage;
    @FXML
    private TextField appliancesName, cityName;
    @FXML
    private TextArea applianceListDisplay;
    @FXML
    private TextArea totalApplianceList;
    
    @FXML
    private Slider slider;
    
 
    public String waatcurr;
    String line;
   
    public int[] prio = new int[100];
    int i=0, j=0, k=0,p=0, l,m,n;
    Double lastDU;
    public int next = 0;
    StringBuilder b = new StringBuilder();
    File file = new File("Power Consumption.txt");
    File file1 = new File("Priorities.txt");
    int lineNumber = 0, timer, lNumber = 0;
    @FXML
    private Label dayConmtn, ranking;
    @FXML
    private Label addPriority;
    @FXML
    private TextField piority;
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    
    
    //Here @FXML
    @FXML
    private void getStartedAction(ActionEvent event) throws Exception{
        variables.appliances = new String[100];
        variables.number = new int[100];
        variables.wattage = new int[100];
        variables.priority = new int[100];
        variables.I = 0;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Get Started.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/fxml/Pictures/bus pic.jpg"));
            stage.setTitle("Get Started");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
            
            //stage.getIcons().add(new Image("/fxml/Pictures/bus pic.jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //edex software engi essentials german munich
    //Here @FXML
    @FXML
    public void addApplianceAction(ActionEvent event) {
        variables.appliances[i] = appliancesName.getText();
        System.out.println(variables.appliances[i]);
        variables.wattage[j] = Integer.parseInt(appliancesWattage.getText());
        System.out.println(variables.wattage[j]);
        variables.number[k] = Integer.parseInt(appliancesNumber.getText());
        
        System.out.println("You have added "+variables.appliances[i]+" "+variables.wattage[j]+" W "+ variables.number[k]+" number of appliances");
        i++; j++; k++;
    }

    //Here @FXML
    @FXML
    private void checkApplianceListAction(ActionEvent event) throws FileNotFoundException {
        variables.I = i;
        for(l =0, m=0, n=0; l<i;l++, m++, n++){
            b.append(variables.appliances[l]+"   "+ variables.wattage[m]+" W "+"    "+" qauantity "+ variables.number[n] + "\n");
        }
        System.out.println(variables.appliances[i-1]);
        b.append("end");
         applianceListDisplay.setText(b.toString());
         try (PrintWriter outputA1 = new PrintWriter(file)) {
            outputA1.println(b);
        }
        catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
         try (PrintWriter outputA1 = new PrintWriter(file1)) {
            outputA1.println("");
        }
        catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            //System.out.println(nameA1);
            System.out.println("Both File Writing built succesfully");
    }
    

    //Here @FXML
    @FXML
    private void generateConsumptionPage(ActionEvent event) throws FileNotFoundException {
        System.out.println("in generate consumption page i="+i);
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Consumption Page.fxml"));
            //FXMLController ob = fxmlLoader.getController();
            //ob.allVariables(appliances, wattage, number);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/fxml/Pictures/bus pic.jpg"));
            stage.setTitle("Input your Consumption");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
            
            //stage.getIcons().add(new Image("/fxml/Pictures/bus pic.jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Here @FXML
    @FXML
    private void LastDayUsageAction(ActionEvent event) {
        lastDU = Double.parseDouble(lastDayUsage.getText());
        System.out.println("you have used "+lastDU+" W last day");
    }
    
    //Here @FXML
    @FXML
    private void wattageAction(ActionEvent event) {
        waatcurr = "wattage";
    }

    //Here @FXML
    @FXML
    private void currencyAction(ActionEvent event) {
        waatcurr = "currency";
    }
    //Here @FXML
        @FXML
        private void clicked(MouseEvent event) {
            if(waatcurr == "wattage"){
                Double prcnt = slider.getValue();
                variables.current = lastDU-((lastDU*prcnt)/100);
                variables.currUsage = String.format("%.2f", variables.current);
//String R = String.format("%.2f", rslt);
                System.out.println(variables.current);
                dayConmtn.setText("you can use "+variables.currUsage+"W "+"today");
            }
            else{
                Double prcnt = slider.getValue();
                variables.current = lastDU-((lastDU*prcnt)/100);
                variables.currUsage = String.format("%.2f", (variables.current/1000)*7);
                System.out.println(variables.current);
                dayConmtn.setText("you will expend "+variables.currUsage+"taka "+"today");
                
            }
        }    
    
    //Here @FXML
    @FXML
    private void fianlAppliancesButton(ActionEvent event) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        
        while ((line = br.readLine()) != null) {
           lineNumber++;
           System.out.println(line);
           b.append(line+"\n");
           }
    }
        catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        totalApplianceList.setText(b.toString());
        timer = lineNumber;
        
    }
    

    //Here @FXML
    @FXML
    private void priorityAction(ActionEvent event) throws FileNotFoundException {
        variables.priority[p] = Integer.parseInt(piority.getText());
        System.out.println("here");
        System.out.println("you have added "+variables.priority[p]+" for "+variables.appliances[p]);
        try(FileWriter fw = new FileWriter("Priorities.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println(variables.priority[p]);
        }   
        catch (IOException e) {
        //exception handling left as an exercise for the reader
        }
        p++;
    }
    
    //Here @FXML
    @FXML
    private void addPriorityAction(ActionEvent event) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
           
        
           line = br.readLine();
           System.out.println("For once only"+line);
           addPriority.setText(line);
           while ((line = br.readLine()) != null) {
           lNumber++;
           ranking.setText("give your priorities from 1 to "+lNumber);
           }
           lNumber = 0;
           
           
    }
        catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //Here @FXML
    @FXML
    private void nextPriorityAction(ActionEvent event) {    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        next++;
        lineNumber = -1;
        while ((line=br.readLine()) != null) {
           lineNumber++;
           //line = br.readLine();
           System.out.println(line);
           if(lineNumber == next){
               addPriority.setText(line);
               break;
           }
           else if(line == "end"){
                addPriority.setText("No more Appliances you added");
           }
        }
    }
        catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //addPriority.setText(str[next]);
    }

    //Here @FXML
    @FXML
    private void weatherPageAction(ActionEvent event) {
        System.out.println("in generate weather page i="+i);
        int I = i;
    try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Weather Info.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //stage.getIcons().add(new Image("/fxml/Pictures/bus pic.jpg"));
            stage.setTitle("Get Started");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
            WeatherInfoController ob = fxmlLoader.getController();
            String city = cityName.getText();
            ob.setCity(city);
            ob.setTex();
            ob.setVars(variables.appliances, variables.wattage, variables.number, variables.priority, variables.I, String.format("%.2f", variables.current));
            //stage.getIcons().add(new Image("/fxml/Pictures/bus pic.jpg"));
            } catch (Exception e) {
                System.out.println(e);
        }
    }
    
    

    
    
}
