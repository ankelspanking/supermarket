/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.scene.control.Button;

/**
 *
 * @author tazos
 */
public class CustomButton extends Button{
     boolean cliked = false;
     int start =0;
     int end = 0;
    public CustomButton(String text , int start ,  int end){       
        super(text);
        //getStylesheets().add("-fx-background-color: #CFCFCF;  -fx-text-fill:black; -fx-font-size:15px");
        this.start =  start;
        this.end = end;
        setStyle("-fx-base: #CFCFCF;");
        setWrapText(false);
        setOnMouseClicked(e->{
            if(!cliked)
            disableButton();
          
        });
       
        
    }
    
public void disableButton(){       
    setStyle("-fx-base: #363636;");   
    cliked = true;   
}    

public void reset(){   
    setStyle("-fx-base: #CFCFCF;");    
    cliked = false;   
}

    void setOnMouseClicked() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
