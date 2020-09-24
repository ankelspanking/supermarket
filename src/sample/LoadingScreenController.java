/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.util.Duration;


public class LoadingScreenController implements Initializable {

    @FXML
    Stage stage;
    @FXML
    Pane pane;
    @FXML
    HBox hBox;
    
    public static Stage stage2;
    private double x = 0;
    private double y = 0;
    
    public static void close(){
        stage2.close();
    }
    public void onClose(ActionEvent event){
        stage.close();
        System.exit(0);
    }
    public void onMini(ActionEvent event){
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stage2 = stage;
        
        
        hBox.setOnMousePressed(e->{
            x = e.getSceneX();
            y = e.getSceneY();
        });
        hBox.setOnMouseDragged(e->{

            stage.setX(e.getScreenX()-x);
            stage.setY(e.getScreenY()-y);
        });
        GoogleLoadingScreen googleLoadingScreen =new GoogleLoadingScreen(10 ,Duration.seconds(0.1),GoogleLoadingScreen.BOUNCE);
        googleLoadingScreen.setLayoutX(120);
        googleLoadingScreen.setLayoutY(40);
        pane.getChildren().add(googleLoadingScreen);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }    
    
}
