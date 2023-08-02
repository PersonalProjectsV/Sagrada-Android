package za.ac.vhuthu.sagrada;
import android.util.Log;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class aDice implements Serializable {
    private static final long serialVersionUID = 121L;
    public int x;
    public dicePos positionn;
    public int xVelocity;
    public int yVelocity;
    public MyPoint target=new MyPoint();
    public MyPoint start=new MyPoint();
    public  int speed;
    boolean selected;
    public String color;

    public transient Button myBtn;
    public ArrayList<String> colors=new ArrayList<>();
    public ArrayList<String> numbers=new ArrayList<>();
    /**
     * The Y coordinate of this <code>Point2D</code>.
     */
    public int y;

    /**
     * Constructs and initializes a <code>Point2D</code> with
     * coordinates (0,&nbsp;0).
     */
    public aDice(int left,int right,int top,int botttom,int xVel,int yVel,String color,Button myBtn){
        positionn=new dicePos(left,right,top,botttom);
        this.xVelocity=xVel;
        this.yVelocity=yVel;
        this.myBtn=myBtn;
        selected=false;
        this.color=color;
        doColors();
        doNumbers();
        initial();
    }

    public String valueString;
    public int curValue;

    public void initial(){
        int ddd = getRandomNumber(1, 6);
        curValue=ddd;

        int colorV=getRandomNumber(0,4);


        String build=colors.get(colorV).substring(0,1)+"_"+numbers.get(ddd);
        color=colors.get(colorV);
        valueString=build;
        curValue = ddd;
    }

    /**
     * after a colission,change the valu of the dice
     */
    public void doValue() {
        int ddd = getRandomNumber(1, 6);
        while (ddd == curValue)
            ddd = getRandomNumber(1, 6);

        curValue = ddd;
        String build=valueString.substring(0,1)+"_"+numbers.get(curValue-1);
        valueString=build;

    }
    public void set(aDice dice){


        x=dice.x;
        positionn.set(dice.positionn);
        xVelocity=dice.xVelocity;
        yVelocity=dice.yVelocity;
        //target.set(dice.target.x,dice.target.y);
        start.set(dice.start.x,dice.start.y);

        speed=dice.speed;
        selected=dice.selected;
        color=dice.color;
        y=dice.y;
        valueString=dice.valueString;
        curValue=dice.curValue;
        Log.d("set",".....................................");


    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * generates a random number between min and max
     * @param min
     * @param max
     * @return
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Boolean isSelected(){
        return selected;
    }

    /**
     * mark dice as selected in pool
     */
    public void select(){
        selected=true;
    }
    public void deselect(){
        selected=false;
    }
    public void doColors(){
        colors.add("red");
        colors.add("yellow");
        colors.add("purple");
        colors.add("green");
        colors.add("blue");
    }
    public void doNumbers() {
        numbers.add("one");
        numbers.add("two");
        numbers.add("three");
        numbers.add("four");
        numbers.add("five");
        numbers.add("six");
    }
}

