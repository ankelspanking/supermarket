package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Order.OrderNode;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Controller implements Initializable  {

    public static Products products ;
    @FXML
    private VBox layout;
    @FXML
    private Pane mainPane;
    @FXML
    private TextField search;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableColumn<OrderNode,String> nameCell;
    @FXML
    private TableColumn<OrderNode,Integer> amountCell;
    @FXML
    private TableColumn<OrderNode,Double> priceCell;
    @FXML
    private TableColumn<OrderNode,Double> totalCell;
    @FXML
    private BorderPane orderLayout;
    @FXML
    private TableView<OrderNode> table;
    @FXML
    private FlowPane dataPane;
    @FXML
    private Button buy;
    @FXML
    private Text companyName;
    @FXML
    private Text companyName1;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView icon;
    @FXML
    private Text title;
    @FXML
    private Stage editStage;
    @FXML
    private Pane companyNamePanel;
    private PopUpMessage popUpMessage;
    private Stage customerOrdersPanel;
    public static IOSToggleSwitch iosToggleSwitch;
    public static ImageView companyLogo;
    public static TableView<OrderNode> tableView;
    public static ChoiceBox box;
    private AddMessage productAddMessage;
    private EditMessage editMessage;
    private VBox deletePane;
    public static BorderPane orderPane;
    public static VBox pane;
    public static FlowPane dataLayout ;
    public static Button buyButtton;
    private static PrintWriter writer;
    private double x;
    private double y;
    ConfermationStage confermationStage;


    private int count = 0;







    public void add(ActionEvent event){
        productAddMessage.show();
    }

    public void onClearOrder(ActionEvent event){
       if(Main.getUnFinshedOrder() != null)
        confermationStage.show();
    }
    public void edit(ActionEvent event){
        if(Main.getUnFinshedOrder() == null)
            editStage.show();
        else
            new ErrorMessage("Please Finish The Current Order Frist");
        // box.getSelectionModel().select(0);
    }

    public  void showCustomerOrderPanel(ActionEvent event){
        customerOrdersPanel.show();
    }
    public static void preformBuy(String name){
        Order order = Main.getUnFinshedOrder();
        if (Main.orders.size() != 0 && order != null) {
            Controller.dataLayout.getChildren().clear();
            order.setEditable(true);
            order.setDeleteable(true);
            order.setFinished(true);
            order.setCustomerName(name);
            products.resetAllProduct();
            tableView.setItems(null);
            if (buyButtton.getText().equals("Buy"))
                writeOrder(order);
            else {
                writerAllOrders();
                HistoryController.historyList.getSelectionModel().select(null);
                buyButtton.setText("Buy");
            }

        }
    }
    public void onBuy(ActionEvent event){
        Order order = Main.getUnFinshedOrder();        
        if(order != null){                                  
            popUpMessage.customerName.setText(order.getCustomerName());
            popUpMessage.show();            
        }
    }

    public static void writerAllOrders(){
        try {
            writer = new PrintWriter("orders.txt","UTF8");
            for (Order order1 : Main.orders)
                writeOrder(order1);

            writer.flush();
        }
        catch (IOException e){
            System.out.println(e + " Controller");
        }
    }

    public static void writeOrder(Order order){

        writer.println(order.getMONTH_YEAR()+"$"+order.getDate()+"$"+order.getTime());
        writer.println(order.getCustomerName());
        for (OrderNode orderNode : order.list)
            writer.println(orderNode.getName()+"$"+orderNode.getPrice()+"$"+orderNode.getAmount());
        writer.println("!");
        writer.flush();
    }
    public void onHistory(ActionEvent event){
        if(Main.getUnFinshedOrder()==null)
        HistoryController.historyStage.show();
        else
            new ErrorMessage("Please Finish The Current Order First");
    }

    public void onDelete(ActionEvent event) {
        if(count % 2 == 0) {
            scrollPane.setContent(DeleteController.tempProductPane);
            ((Button)event.getSource()).setStyle("-fx-background-color:#A6ACAF");
        }
        else {
            scrollPane.setContent(layout);
            ((Button)event.getSource()).setStyle("-fx-background-color:  linear-gradient(to right, #4286f4, #373b44)");
        }

        count++;

    }
    public void onClose(ActionEvent event){
        try {
           PrintWriter writer = new PrintWriter("setup","UTF8");
           writer.println(companyName.getText());
           writer.println(Main.imagePath);
           if(iosToggleSwitch.isSwitchedOn())
               writer.println("on");
           else
               writer.println("off");
            
            writer.close();
        }catch (IOException e){
            System.out.println(e + " Controller,s writer");
        }
        ((Stage)((Button) event.getSource()).getScene().getWindow()).close();
        System.exit(0);
    }
    public void onMini(ActionEvent event){
        ((Stage)((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void onPress(MouseEvent event){
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void onDrag(MouseEvent event){

        ((AnchorPane)event.getSource()).getScene().getWindow().setX(event.getScreenX()-x);
        ((AnchorPane)event.getSource()).getScene().getWindow().setY(event.getScreenY()-y);
    }






    @Override
    public void initialize(URL location, ResourceBundle resources){
        
        popUpMessage = new PopUpMessage((name)->{            
               preformBuy((String)name);
               popUpMessage.close();
           });
        
        tableView =table;
        orderPane=orderLayout;
        dataLayout = dataPane;
        buyButtton = buy;
        String name = Main.name;
        companyLogo =logo;
        logo.imageProperty().addListener(e->icon.setImage(logo.getImage()));
        companyName.setText(name);
        companyName1.setText(name);
        title.setText(name);
        
         
        

        confermationStage = new ConfermationStage("Are You Sure You Want To Delete This Order?", "هل انت متاكد انك تريد مسح هذا طلب؟", e->{            
            Order order = Main.getUnFinshedOrder();            
            order.clearOrder();
            tableView.setItems(null);             
        });
        try {

            FXMLLoader.load(getClass().getResource("historyPane.fxml"));
            editStage = FXMLLoader.load(getClass().getResource("FXMLedit.fxml"));
            deletePane = FXMLLoader.load(getClass().getResource("FXMLdelete.fxml"));            
        }
        catch(IOException e){
            System.out.println(e.getMessage() + " Controller,s initialize 1");
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " Controller,s initialize");
        }

        box = new ChoiceBox();
        pane=layout;

        productAddMessage = new AddMessage();
        products = new Products();
       Main.read();
        try {
            writer = new PrintWriter("orders.txt","UTF8");
        }catch (IOException e){
            System.out.println(e + " Controller,s writer");
        }
        
        for (Order order :Main.orders)
            writeOrder(order);

        search.textProperty().addListener(e->{
            pane.getChildren().clear();
            DeleteController.tempProductPane.getChildren().clear();
            if(search.getLength() != 0){
                for(int i  = 0 ;  i < products.size();i++) {
                    Product product = products.getProduct(i);
                    if (product.getProductName().toLowerCase().startsWith(search.getText().toLowerCase())) {
                        pane.getChildren().add(product.getLayout());
                        DeleteController.addToDeletePane(product);
                    }
                }

            }else
                for(int i  = 0 ;  i < products.size();i++) {
                    layout.getChildren().add(products.getProduct(i).getLayout());
                    DeleteController.addToDeletePane(products.getProduct(i));
                }


        });


        nameCell.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountCell.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceCell.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalCell.setCellValueFactory(new PropertyValueFactory<>("total"));

        iosToggleSwitch = new IOSToggleSwitch(60 , 30);
        FlowPane flowPane = new FlowPane(new ImageView("images/sun.png"), iosToggleSwitch , new ImageView("images/moon.png"));
        flowPane.setHgap(10);
        flowPane.setLayoutX(mainPane.getPrefWidth()-170);
        flowPane.setLayoutY(15);
        mainPane.getChildren().add(flowPane);
        iosToggleSwitch.switchedOnProperty().addListener(e->{
            
            Order order = Main.getUnFinshedOrder();
            if(order != null)
                order.setTextColor();
               
            
            if(iosToggleSwitch.isSwitchedOn()) {
                mainPane.getStylesheets().add("Styles/lightMode.css");
                mainPane.getStylesheets().add("Styles/darkMode.css");
                dataLayout.setStyle("-fx-background-color: #203A43"); 
                companyNamePanel.setStyle("-fx-background-color: #203A43"); 
                companyName1.setStyle("-fx-fill: white");
            }
            else{
                mainPane.getStylesheets().remove(mainPane.getStylesheets().size()-1);
                dataLayout.setStyle("-fx-background-color: #416473");
                companyName1.setStyle("-fx-fill: black");
                companyNamePanel.setStyle("-fx-background-color: #416473");//#416473                
            }

        });
        try{
            customerOrdersPanel = FXMLLoader.load(getClass().getResource("customerOrderPanel.fxml"));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }  
        
        /*
        Runnable runnable = ()->{
            try {
                FXMLLoader.load(getClass().getResource("loadingScreen.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        Thread thread  = new Thread(runnable);
        
        thread.start();
        */
    }


}
