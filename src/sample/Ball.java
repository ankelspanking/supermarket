package sample;

import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;



public class Ball extends Circle {

    private enum Type {BOUNCE , MOVERIGHT,MOVELEFT}
    private TranslateTransition movingTransition;
    private double coordinates;
    private Transition transition;
    private Duration duration;
    public static final Type BOUNCE = Type.BOUNCE;
    public static final Type MOVELEFT = Type.MOVELEFT;
    public static final Type MOVERIGHT = Type.MOVERIGHT;
    Ball(double radius , Duration duration , double moveTo,Type type){
        coordinates = moveTo;
        this.duration = duration;
        setRadius(radius);
        movingTransition = new TranslateTransition(duration,this);
        if(type == Type.BOUNCE)
        bounce();
        else if(type == Type.MOVELEFT)
         moveToTheLeft();
        else
            moveToTheRight();
    }
    Ball(double radius , Duration duration , double moveTo, Type type , Color color){
        this(radius ,duration , moveTo,type);
        setFill(color);
    }


    private void bounce(){
        TranslateTransition translateTransition = new TranslateTransition(duration,this);
        translateTransition.setToY(0);
        movingTransition.setToY(-coordinates);
        transition = new SequentialTransition(movingTransition,translateTransition);
    }

    public Transition getTransition() {
        return transition;
    }
    private void moveToTheLeft(){
        movingTransition.setToX(-coordinates);
        transition = movingTransition;
    }
    private void moveToTheRight(){
        movingTransition.setToX(coordinates);
        transition = movingTransition;
    }
    public void setDelay(double delay){
        transition.setDelay(Duration.seconds(delay));
    }

}
