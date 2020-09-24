package sample;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javax.swing.JOptionPane;
import java.io.IOException;

public class ErrorMessage{

    public ErrorMessage(String message) {
        try{
            Stage root = FXMLLoader.load(getClass().getResource("ErrorMessage.fxml"));
            ErrorController.tempText.setText(message);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null , e.getMessage());
        }

    }






}

