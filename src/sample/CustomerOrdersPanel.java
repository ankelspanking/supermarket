package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.Order.OrderNode;
import sample.Main;
import sample.Order;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;import javafx.beans.Observable;
import javafx.collections.ObservableList;


public class CustomerOrdersPanel implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TextField textField;
    @FXML
    private Label name;
    @FXML
    private TableColumn<OrderNode,String> nameColumn;
    @FXML
    private TableColumn<OrderNode,Double> priceColumn;
    @FXML
    private TableColumn<OrderNode,Integer> amountColumn;
    @FXML
    private TableColumn<OrderNode,Double> totalPriceColumn;
    @FXML
    private TableColumn<OrderNode,String> dateColumn;
    @FXML
    private ListView listView;
    @FXML
    private Stage stage;
    @FXML
    private HBox hBox;
    private double x = 0;
    private double y = 0;
    private LinkedList<String> stringOfNames;
    private LinkedList<Text> names;
    private PopUpMessage popUpMessage;
    ConfermationStage confermationStage;

    public void deleteUser(ActionEvent event){
        String name = this.name.getText();
        if (!name.isEmpty())
            confermationStage.show();
        
    }
    
    public void editUser(ActionEvent event){
        String customerName = this.name.getText();
         if(!customerName.isEmpty()){                                  
            popUpMessage.customerName.setText(customerName);
            popUpMessage.show();            
        }        
    }
    
    private void deleteName(String deletedName){
        for(Text name: names )
            if(name.getText().equalsIgnoreCase(deletedName)){
                names.remove(name);
                break;
            }
    }
    @FXML
    private void syncData(WindowEvent event){
       sync();
    }

    public void onClose(ActionEvent event){
        stage.close();
    }
    public void onMini(ActionEvent event){
        stage.setIconified(true);
    }
    
    private boolean cheackForSameName(String name){
                      
           ObservableList list = listView.getItems();
           for(int i =0; i < list.size();i++)                             
                   if(name.equalsIgnoreCase(((Text)list.get(i)).getText()))
                       return true;
               
                      
            return false;
    }

    private void sync(){
        name.setText("");
        stringOfNames.clear();
        names = new LinkedList();
        listView.getItems().clear();
        table.getItems().clear();
        for( Order order : Main.orders){
            String name = order.getCustomerName();
            if(!stringOfNames.contains(name)){
                stringOfNames.add(name);
                names.add(new Text(name.toLowerCase()));
            }            
        }
        
        listView.setItems(FXCollections.observableList(((LinkedList)names.clone())));
    }
    
    public static void addOrderToCustomersList(Order order){
        
    }
    private String getSelectedName(){
        Object selectedItem = listView.getSelectionModel().getSelectedItem();
        if(listView.getItems().size() != 0 && selectedItem != null)
            return ((Text)selectedItem).getText();

        return null;
    }
    public void searchForUser(KeyEvent event){
        LinkedList<Text> filteredNames = new LinkedList<>();
        String text =textField.getText().toLowerCase();
        if(!text.isEmpty()) {
            for (Text name : names)
                if (name.getText().toLowerCase().startsWith(text))
                    filteredNames.add(name);

            listView.setItems(FXCollections.observableList(filteredNames));
        }else
            listView.setItems(FXCollections.observableList(names));
        
        

        /*
        for(Order order : Main.orders) {
            Label dateLabel = new Label(order.getDate() + " " + order.getTime());
            dateLabel.setFont(new Font(17));
            FlowPane date =  new FlowPane();
            date.setAlignment(Pos.CENTER);
            //orderPanel.getChildren().add(date);
            if (order.getCustomerName().equalsIgnoreCase(textField.getText())) {
                for (Order.OrderNode orderNode : order.list) {
                    //orderPanel.getChildren().add(makeCustomerOrderHistoryNode(orderNode));
                }
            }
        }
        */
    }
/*
    private HBox makeCustomerOrderHistoryNode(Order.OrderNode orderNode){
         Label productName =  new Label(orderNode.getName());
         Label price =  new Label(String.valueOf(orderNode.getPrice()));
         Label amount =  new Label(String.valueOf(orderNode.getPrice()));
         Label totalPrice = new Label(String.valueOf(orderNode.getTotal()));
         productName.setFont(new Font(15));
         price.setFont(new Font(15));
         amount.setFont(new Font(15));
         totalPrice.setFont(new Font(15));
         HBox layout =  new HBox(productName , price, amount, totalPrice);
         layout.setPadding(new Insets(0,0,0,10));
         layout.setPrefSize(width , 20);
         layout.setStyle("-fx-background-color: red");
         layout.setSpacing(180);
         return layout;

    }
*/

    private Boolean isNameExists(String name){
        for( Text text : names)
            if (((Text) text).getText().equalsIgnoreCase(name))
                return true;

        return false;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stringOfNames =  new LinkedList();
        popUpMessage = new PopUpMessage((name)->{
             String oldName =  this.name.getText();
             if(!((String)name).equalsIgnoreCase(oldName)){
                Main.orders.forEach(order->{
                    if(order.getCustomerName().equalsIgnoreCase(oldName))
                        order.setCustomerName((String)name);                                                          
                });
                
                if(cheackForSameName((String)name)){
                    System.out.println("enn");
                    sync(); 
                }
                else{                    
                    ((Text)listView.getSelectionModel().getSelectedItem()).setText(((String)name).toLowerCase());
                    this.name.setText((String)name);
                }
                popUpMessage.close();
                Controller.writerAllOrders();                              
             }
            else
                 popUpMessage.close();
        });

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        listView.getSelectionModel().selectedItemProperty().addListener(e->{
            LinkedList<OrderNode> usersOrders = new LinkedList<>();
            String name = getSelectedName();
            if(name != null) {
                this.name.setText(name);
                for(Order order : Main.orders)
                    if(order.getCustomerName().equalsIgnoreCase(name))
                        for (OrderNode orderNode : order.list)
                            usersOrders.add(orderNode);
                table.setItems(FXCollections.observableList(usersOrders));
            }

            // System.out.println(product.getProductName() + " " + product.getPrice() );
        });

        hBox.setOnMousePressed(e->{
            x = e.getSceneX();
            y = e.getSceneY();
        });
        hBox.setOnMouseDragged(e->{

            stage.setX(e.getScreenX()-x);
            stage.setY(e.getScreenY()-y);
        });

        confermationStage = new ConfermationStage("Are You Sure You Want To Delete This Prodcut?", "هل انت متاكد انك تريد مسح هذا منتج ؟", 
                e->{
                    String name = this.name.getText();
                    LinkedList<Order> temp = new LinkedList();
                    for(Order order : Main.orders)
                        if (order.getCustomerName().equalsIgnoreCase(name))
                            temp.add(order);
                            //order.deleteOrder();                
                    deleteName(name);
                    table.getItems().clear();
                    listView.getItems().remove(listView.getSelectionModel().getSelectedItem());   
                    
                     for(Order order : temp)
                        order.deleteOrder();
                     
                    if(names.size()==0) 
                        this.name.setText(""); 
                });

        

    }
}
