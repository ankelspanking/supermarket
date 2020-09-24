package sample;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class EditMessage  {

    public EditMessage()throws Exception{

        Stage root = FXMLLoader.load(getClass().getResource("FXMLedit.fxml"));

    }

    public void show(){
        EditController.show();
    }



}

