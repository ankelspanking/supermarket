package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.Order;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;


public class HistoryController implements Initializable {


    @FXML
    private Stage stage;
    @FXML
    private  TreeTableView<String> historyTree;
    @FXML
    private FlowPane historyLayout;
    @FXML
    private TreeTableColumn<String,String> ordersColumn;
    @FXML
    private HBox buttonsPanel;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    
    private CustomButton lastButton;
    public static ObservableList<String> dates;
    public LinkedList<Integer> selectedItemList;
    public static Stage  historyStage;
    public static TreeTableView<String> historyList;
    public static FlowPane historyPane;
    public  static TreeTableColumn<String,String> column;
    static int indexOfItem = 0;
    int lodedOrders = 0; 
    int startIndex = 0;
    int endIndex = 0;
    int incrementBy =0;
    public static void addMonth_Year(String date , String time , String month_year ){
        boolean exists = false;

        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i).equals(month_year)) {
                exists = true;
                indexOfItem = i;
                i = dates.size();
            }
        }


        if(exists == false) {
            dates.add(month_year);
            TreeItem<String> treeItem = new TreeItem<>(month_year );
            addTime(addDate(treeItem,date),time);
            historyList.getRoot().getChildren().add(treeItem);

        }else
            addTime(addDate(historyList.getRoot().getChildren().get(indexOfItem),date),time);


    }
    private static TreeItem addDate(TreeItem<String> rootItem , String date){
        boolean exists = false;
        TreeItem<String> treeItem = null;
        for (TreeItem<String> temp : rootItem.getChildren()){
            if(temp.getValue().equals(date)) {
                treeItem = temp;
                exists = true;
                break;
            }
        }
        if(!exists) {
            treeItem = new TreeItem<>(date);
            rootItem.getChildren().add(treeItem);
        }
        return treeItem;
    }
    private static void addTime(TreeItem<String> subItem , String time){
        subItem.getChildren().add(new TreeItem<>(time));
    }
    
    private void addToButtonPanel(int count, int lastEnd ,  int nextEnd){
        
         CustomButton button = new CustomButton(String.valueOf(count),lastEnd,(nextEnd));                         
            buttonsPanel.getChildren().add(button);
            button.setOnAction(event-> {
                if(lastButton != null)
                    lastButton.reset();
                
                lastButton =  button;
                historyPane.getChildren().clear();
                showOrders( button.start, button.end);
                
                
            });
    }

    public void showOrders(int start ,  int end){
       
         historyLayout.getChildren().clear();
         
        
        for(int i = start; i <= end; i++)
            historyLayout.getChildren().add(makeHistoryLayout(Main.orders.get(selectedItemList.get(i))));
        
       
        /*
        int count = 0;
        if(historyList.getSelectionModel().getSelectedItem() != null) {
            String selectedItem = historyList.getSelectionModel().getSelectedItem().getValue();
            if (selectedItem.contains(":")) {
                for (int i = start; i <= end; i++) {
                        historyPane.getChildren().add(makeHistoryLayout(Main.orders.get(selectedItemList.get(i))));
                    
                    System.out.println("Enetred if" + (++count));
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    
                    historyPane.getChildren().add(makeHistoryLayout(Main.orders.get(selectedItemList.get(i))));
                    Order order = Main.orders.get(lodedOrders++);
                    
                    if (order.getDate().startsWith(selectedItem.substring(0, 2)) && order.getDate()
                            .endsWith(selectedItem.substring(selectedItem.length() - 2)))
                        historyPane.getChildren().add(makeHistoryLayout(order));
                    
                    
                    if(selectedItem.split("/").length == 3){
                        if(order.getDate().equals(selectedItem))
                            historyPane.getChildren().add(makeHistoryLayout(order));
                    } 
                    else
                        if(order.getMONTH_YEAR().equals(selectedItem))    
                            historyPane.getChildren().add(makeHistoryLayout(order));    
                    System.out.println("Enetred Else" + lodedOrders);
                }
                
            }
        }
*/
    }

    
  
    
    private VBox makeHistoryLayout(Order order){
        Text totalMoney = new Text(order.totalMoney.getText()) ;     
        /*
        order.totalMoney.textProperty().addListener(e->{
            totalMoney.setText(order.totalMoney.getText());
        });   
        */
        FlowPane flowPane = new FlowPane(new Text(order.getTime()),totalMoney, new Text("Customer Name : " +order.getCustomerName().toLowerCase()));
        
        flowPane.setHgap(10);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setStyle("-fx-background-color:#21F783");
        flowPane.setPrefSize(100,30);
        HBox hBox= new HBox(order.delete , order.edit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(30);
        VBox vBox = new VBox(flowPane, order.table , hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(3);
        return vBox;
    }
    
    public void onClickLeft(ActionEvent e){
                
                endIndex--;
                startIndex--;                   
                startIndex -=19;                
                endIndex -=incrementBy;                
                incrementBy = 19;
                showOrders(startIndex, endIndex);
                rightButton.setDisable(false);
                if(startIndex == 0)
                leftButton.setDisable(true);            
    }
    
    public void onClickRight(ActionEvent e){
            int defffrance = selectedItemList.size()-1  - (endIndex);
            
            leftButton.setDisable(false);
            if( defffrance > 20){                
                incrementBy = 19;
                startIndex = ++endIndex ;
                endIndex+=incrementBy;
                System.out.println(startIndex + " "+ endIndex);
                showOrders(startIndex, endIndex);
            }
            else{                
                incrementBy = --defffrance;
                startIndex = ++endIndex;
                endIndex+=incrementBy;
                System.out.println(startIndex + " "+ endIndex);                
                showOrders(startIndex, endIndex);
            }
            
            if(endIndex == selectedItemList.size()-1)
                rightButton.setDisable(true);                        
    }

    public static void removeTime(String month_year , String date,String time){
        int i;
        for (i = 0; i < dates.size();i++)
            if(dates.get(i).equals(month_year))
                break;
        TreeItem<String > root =historyList.getRoot();       
        TreeItem<String> subRoot =root.getChildren().get(i);     
        TreeItem<String> subTree  = null;

        for(TreeItem<String> temp : subRoot.getChildren())
            if (temp.getValue().equals(date)) {
                subTree = temp;
                break;
            }

        for(TreeItem<String>temp:subTree.getChildren())
            if (time.equals(temp.getValue())) {
                subTree.getChildren().remove(temp);
                break;
            }



        if(subTree.getChildren().size() == 0)                                                    
            subRoot.getChildren().remove(subTree);
             
          
           
        
        
        if(subRoot.getChildren().size() == 0){
            root.getChildren().remove(subRoot);
            dates.remove(i);
        }

        //historyPane.getChildren().clear();
        //System.out.println("value "+subTree.getValue());
        //System.out.println(subTree==null? subRoot :subTree);
        TreeItem<String > treeItem = historyList.getSelectionModel().getSelectedItem();
        historyList.getSelectionModel().select(null);
        historyList.getSelectionModel().select(treeItem);


        //}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historyStage=stage;
        historyPane=historyLayout;
        historyList = historyTree;
        column = ordersColumn;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        historyPane.setPadding(new Insets(10));
        dates = FXCollections.observableArrayList();
        historyList.setRoot(new TreeItem<>("Dates"));
        historyList.getRoot().setExpanded(true);
        historyList.setShowRoot(false);
        selectedItemList =  new LinkedList();
        stage.setOnCloseRequest(e->{
            historyList.getSelectionModel().select(null);
            buttonsPanel.getChildren().clear();
                });
        
        stage.setOnShowing(e->{
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            historyLayout.getChildren().clear();
            //Main.stage.close();
        });
        historyList.getSelectionModel().selectedItemProperty().addListener(e->{
            
            
            selectedItemList.clear();
            TreeItem<String> selectedItem =  historyList.getSelectionModel().getSelectedItem();
            if(selectedItem!= null) {
                    
                    String valueOfselectedItem = selectedItem.getValue();
                    
                    if (valueOfselectedItem.contains(":")) {
                        for (int i = 0; i < Main.orders.size(); i++) {
                            Order order =  Main.orders.get(i);
                            if(order.getTime().equals(valueOfselectedItem) && order.getDate().equals(selectedItem.getParent().getValue()))
                                selectedItemList.add(i);                            
                        }
                    }
                    else{
                        
                        for (int i = 0; i < Main.orders.size(); i++) {                                               
                            Order order = Main.orders.get(i);
                           

                            if(valueOfselectedItem.split("/").length == 3){
                                if(order.getDate().equals(valueOfselectedItem))
                                    selectedItemList.add(i);
                            } 
                            else
                                if(order.getMONTH_YEAR().equals(valueOfselectedItem))    
                                        selectedItemList.add(i);   
                            
                        }                        
                    }
                    leftButton.setDisable(false);
                    rightButton.setDisable(false);
                    if(selectedItemList.size() >20){
                        startIndex= 0;
                        endIndex = 19;
                        showOrders(0, 19);
                        leftButton.setDisable(true);
                     }
                    else{                            
                        showOrders(0, selectedItemList.size()-1);
                        leftButton.setDisable(true);
                        rightButton.setDisable(true);
                    }
                    /*
                    int lastEnd = 0;
                    int count = 1;
                    buttonsPanel.getChildren().clear();
                    while((selectedItemList.size()  - lastEnd) >= 20){
                        addToButtonPanel(count++, lastEnd, (lastEnd+=19));
                        lastEnd++;
                        System.out.println("out the function "+ count);
                    }
                    int deffrence = selectedItemList.size()  - lastEnd;
                    if( deffrence < 20 && !(deffrence <= 0))
                        addToButtonPanel(count, lastEnd,selectedItemList.size()-1);
                    */
            }
            
            //showOrders();
        });
        ordersColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<String, String> param) {
                return new SimpleStringProperty(param.getValue().getValue());
            }
        });
               
    }
}
