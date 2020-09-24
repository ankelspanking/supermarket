/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class ConfermationStage {
  private BorderPane layout ;
    private Stage frame;
    protected Button yes ;
    protected Button no ;
   
    private HBox bottomLayout ;
    
    
    public ConfermationStage(String text, String textInArabic,Consumer func ){
     bottomLayout = new HBox();
        frame = new Stage();
        frame.setTitle("Enter Customer's Name");
        frame.initModality(Modality.APPLICATION_MODAL);
        
        Text explainText = new Text(text);//Are You Sure You Want To Delete This Item
        Text explainTextInArbic = new Text(textInArabic);//هل انت متاكد انك تريد مسح هذا المنتج
        explainText.setFont(new Font(20));
        explainTextInArbic.setFont(new Font(20));
        
     
        
        VBox centerdPanel =  new VBox(explainText, explainTextInArbic  );
        centerdPanel.setSpacing(5);
        //centerdPanel.setPadding(new Insets(10, 0, 0, 0));
        centerdPanel.setAlignment(Pos.CENTER);
        frame.setResizable(false);
        frame.getIcons().add(new Image("images/delete.png"));
        yes = new Button("Yes");
        no = new Button("No");
        bottomLayout.getChildren().addAll(yes, no);
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.setSpacing(20);
        layout = new BorderPane();
        
        layout.setCenter(centerdPanel);
        layout.setBottom(bottomLayout);
       
        
        frame.setScene(new Scene(layout , 410, 100));
        yes.setOnAction(e-> {
            func.accept(this);
            frame.close();
        });
        
        no.setOnAction(e-> frame.close());
   }
    
    public void show( ) {
       frame.show();
    }
    
}
