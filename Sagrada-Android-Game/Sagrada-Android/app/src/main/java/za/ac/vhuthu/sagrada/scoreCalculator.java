package za.ac.vhuthu.sagrada;

import java.util.ArrayList;

public class scoreCalculator extends Thread {
    int score=0;
    SagradaWindow theWindow;
    String color;

    public scoreCalculator(SagradaWindow window,String playerColor){
        this.theWindow=window;
        this.color=playerColor;
    }
    public int doScore(){
        int score=0;
        score+=doInColorAndEmpty();


        int toAddColumns=doColumns()*5;

        score+=toAddColumns;



        return score;
    }
    //calculate how many columns dont have repeating colors
    private int doColumns(){
        int dontRepeat=0;
        for (int x=0;x<5;x++){
            ArrayList colors=new ArrayList();
            for(int y=0;y<4;y++){
                myImageButton cur=theWindow.windBtns[y][x];
                if(!colors.contains(cur.color))
                    colors.add(cur.color);
                else {
                    dontRepeat--;
                    break;
                }
            }
            dontRepeat++;
        }
        if(dontRepeat<=0)
            return 0;
        return dontRepeat;
    }
    //calculate score of windows with dices in them and those which are empty
    private int doInColorAndEmpty(){
        int score=0;
        for(int x=0;x<4;x++){
            for (int y=0;y<5;y++){
                myImageButton cur=theWindow.windBtns[x][y];
                aDice dice=cur.value;
                if(dice==null)
                    score--;
                else if(dice.color.equals(color)){
                    score++;
                }
            }
        }
        return score;
    }
}
