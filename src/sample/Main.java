package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Main extends Application {

    public static LinkedList<Order> orders = new LinkedList<>();
    public static BufferedReader reader;
    public static String name = "";
    public static String imagePath;
    public static Stage stage;
    
    public static boolean doneLoadingOrders = false;

    public static Order getUnFinshedOrder(){
        for(Order order : Main.orders)
            if (!order.isFinished())
                return order;

        return  null;
    }



    public static void read(){

        try
        {
            String line =reader.readLine();

            while (line != null){
                // System.out.println(line);
                int first$ =  line.indexOf("$")+1;

                int second$ = line.indexOf("$" ,first$)+1;
                
                Order order = new Order(line.substring(0,first$-1),line.substring(first$ , second$-1),line.substring(second$));
                
                order.setCustomerName(reader.readLine());
                order.setFinished(true);
                order.setEditable(true);
                orders.add(order);
                line = reader.readLine();
                
                while (!line.equals("!")) {
                    first$ = line.indexOf("$") + 1;
                    second$ = line.indexOf("$", first$) + 1;
                    String name = line.substring(0, first$ - 1);
                    double price = Double.parseDouble(line.substring(first$, second$ - 1));
                    Product product =Controller.products.getProduct(name);
                    order.addOrder(name, price, (product == null? new Label(""):product.count));
                    order.list.get(order.list.size() - 1).setAmount(line.substring(second$));
                    line = reader.readLine();
                    if(!order.edit.isDisable()) {

                        if(product==null || product.getPrice() != price)
                            order.setEditable(false);
                   }

                }
                line = reader.readLine();
            }
            System.err.println("Done Reading");
            doneLoadingOrders = true;
        }catch (Exception e){
            System.out.println(e + " Main");
        }

        Controller.tableView.setItems(null);
        Controller.dataLayout.getChildren().clear();
    }

    public static void sync(Product product){
        if(!orders.isEmpty()){
            for (Order order : orders)
                if(order.exists(product.getProductName()) && order.isFinished())
                    order.setEditable(false);

            if(orders.getLast().exists(product.getProductName()) && !orders.getLast().isFinished()) {
                orders.getLast().clearOrder();
                Controller.products.resetAllProduct();
            }
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception{


    try {
        File file = new File("orders.txt");
        //Scanner s = new Scanner(file);
        if (file.exists())
            reader = new BufferedReader(new FileReader(file));
        if (!new File("setup").exists()) {
            Stage stage = FXMLLoader.load(getClass().getResource("openingScreen.fxml"));
            stage.show();
            OpeningScreenControl.onDoneProperty().addListener(e -> {
                createStage(primaryStage);
            });
        } else
            createStage(primaryStage);
    }catch(Exception e){
            System.out.println(e.getMessage() + " at Main class 113");
        }

    }

    private void createStage(Stage primaryStage){
        try {

            BufferedReader reader = new BufferedReader(new FileReader("setup"));
            name = reader.readLine();
            imagePath = reader.readLine();
            String mood = reader.readLine();
            /*
            Runnable runnable = ()->{
                try {
                    FXMLLoader.load(getClass().getResource("loadingScreen.fxml"));
                } catch (IOException ex) {
                    
                }
                };
            Thread thread = new Thread(runnable);
            thread.start();
            */
            Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            
            
            if(mood.equals("on"))
                Controller.iosToggleSwitch.switchOn();
           
            ImageView image = null ;
                 if(!imagePath.contains("images/"))
                 image = new ImageView(new Image(new FileInputStream(imagePath)));
                 else
                     image = new ImageView(new Image("images/icon.png"));

            Controller.companyLogo.setImage(image.getImage());
            
            primaryStage.setTitle(name);
            Scene scene = new Scene(root, 1310, 715);
            primaryStage.getIcons().add(image.getImage());
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setResizable(false);
            primaryStage.show();
            stage = primaryStage;

        }catch (IOException e){
            System.out.println(e.getMessage() +" create method in Main Class");
        }

    }



    public static void main(String[] args) throws FileNotFoundException {
        
   
        launch(args);
       /*
        PrintWriter print = new PrintWriter("orders.txt");
        for(int i =0; i <10000; i++){
            
            print.println("06/2020$06/27/2020$02:06:22 AM");           
           
            print.println("ahemd ahmed mohmaed ahmed" + i);
            for(int p = 0; p < 10; p++)
                print.println("product"+p+"$100.0$10");
            
            print.println("!");
            
            System.out.println(i);
        
    }
        print.close();
*/
    }
}
