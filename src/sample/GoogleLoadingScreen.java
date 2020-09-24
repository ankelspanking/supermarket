package sample;


import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GoogleLoadingScreen extends Pane {

    private Transition transition;
    private Ball  b1;
    private Ball b2;
    private Ball b3;
    private Ball b4;
    private double radius;
    private Duration duration;
    private enum Type {bounce , move};
    public static final Type BOUNCE =  Type.bounce;
    public static final Type MOVE = Type.move;
    private Type type;



    GoogleLoadingScreen(double radius , Duration duration ,  Type type){
        setStyle(" -fx-background-color:red");
        this.radius= radius;
        this.duration = duration;
        this.type = type;

         createObject();
        
        setStyle("-fx-background-color : transparent");
       play();

    }
    GoogleLoadingScreen(Double radius){
        this(radius  ,Duration.seconds(0.07) , Type.bounce);
    }
    private void stop(){
        getChildren().clear();
         createObject();
    }
    public void play(){
        transition.play();
    }
    private void createObject(){
        b1  = new Ball(radius, duration , 20.0,Ball.BOUNCE, Color.web("#267EE7"));
        b2  = new Ball(radius, duration , 20.0,Ball.BOUNCE,Color.web("#EF322F"));
        b3  = new Ball(radius, duration , 20.0,Ball.BOUNCE,Color.web("#F1C40F"));
        b4  = new Ball(radius, duration , 20.0,Ball.BOUNCE,Color.web("#28B463"));

        if(type == Type.bounce)
            bounce();
        else
            move();

        b2.setLayoutX(radius*3);
        b3.setLayoutX(radius*6);
        b4.setLayoutX(radius*9);
        getChildren().addAll(b1 ,b2 ,b3,b4);
        transition.setCycleCount(10000000);
    }
    private void move(){
        transition = new ParallelTransition(b1.getTransition(),b2.getTransition(),b3.getTransition(),b4.getTransition());
        b2.setDelay(0.08);
        b3.setDelay(0.15);
        b4.setDelay(0.20);
    }
    private void bounce(){
        transition = new SequentialTransition(b1.getTransition(),b2.getTransition(),b3.getTransition(),b4.getTransition());
    }
    public void setCycleCount(int cycles){
        transition.setCycleCount(cycles);
    }


}
