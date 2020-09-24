package sample;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class PopUpMessage {
    private BorderPane layout ;
    private Stage frame;
    protected Button save ;
    protected Button close ;
   
    private HBox bottomLayout ;
    public  TextField customerName;

    public PopUpMessage(Consumer func) {
        bottomLayout = new HBox();
        frame = new Stage();
        frame.setTitle("Enter Customer's Name");
        frame.initModality(Modality.APPLICATION_MODAL);
        
        Text expleanText = new Text("Please Enter The Name of The Customer  ");
        Text exmpleanTextInArbic = new Text("ادخل اسم العميل ربعيا");
        expleanText.setFont(new Font(20));
        exmpleanTextInArbic.setFont(new Font(20));
        
        customerName = new TextField();
        
        VBox centerdPanel =  new VBox(expleanText, exmpleanTextInArbic ,customerName );
        centerdPanel.setSpacing(20);
        //centerdPanel.setPadding(new Insets(10, 0, 0, 0));
        centerdPanel.setAlignment(Pos.CENTER);
        frame.setResizable(false);
        frame.getIcons().add(new Image("images/add.png"));
        save = new Button("Save");
        close = new Button("Close");
        bottomLayout.getChildren().addAll(save, close);
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.setSpacing(20);
        layout = new BorderPane();
        
        layout.setCenter(centerdPanel);
        layout.setBottom(bottomLayout);
       
        
        frame.setScene(new Scene(layout , 500, 200));
        save.setOnAction(e->{
            String name = customerName.getText();
                 
            if(name.isEmpty()){
                new ErrorMessage("Tha customer name is empty");
            }
            else{
                if(name.split(" ").length == 4){
                    func.accept(name);
                }else
                    new ErrorMessage("Please Enter the customer's name quarterly. رجان ادخل اسم العميل ربعيا");
            }
        });
        close.setOnAction(e->{
            close();
            customerName.setText("");
        });
    }


    public void any(Order order){
        Date date1 = new Date();
        order = new Order(new SimpleDateFormat("MM/y").format(date1),
                new SimpleDateFormat("MM/dd/y").format(date1),new SimpleDateFormat("hh:mm:ss a").format(date1));
        Main.orders.add(order);

    }
    public void show(){
        frame.show();
    }
    protected void close(){
        frame.close();
    }

    

    protected BorderPane getLayout() {
        return layout;
    }


}