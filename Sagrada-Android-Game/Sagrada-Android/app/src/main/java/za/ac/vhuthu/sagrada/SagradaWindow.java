package za.ac.vhuthu.sagrada;


import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.widget.TableLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class SagradaWindow implements Serializable {
    private static final long serialVersionUID = 109L;
    TableLayout theWindow;
    int round=-1;
    myImageButton[][] windBtns=new myImageButton[4][5];
    public SagradaWindow(TableLayout theWindow){
        this.theWindow=theWindow;

    }

    public TableLayout getTheWindow() {
        return theWindow;
    }
    public void activate(aDice clicked,int round){
        round=round;
        if(round==1){
            for(int x=0;x<5;x++)
            {
                myImageButton cur=windBtns[0][x];
                changeBack(cur,false,clicked,round);
                cur.activeReceive=true;

                myImageButton curr=windBtns[3][x];
                changeBack(curr,false,clicked,round);

            }
            for(int x=0;x<4;x++)
            {
                myImageButton cur=windBtns[x][0];
                changeBack(cur,false,clicked,round);

                myImageButton curr=windBtns[x][4];
                changeBack(curr,false,clicked,round);
            }
        }
        else doActivating(clicked);
    }
    public void changeBack(myImageButton btn,boolean type,aDice going,int round){
        if(!type)
        {
            if(round==1){
                btn.img.setBackgroundColor(Color.RED);
                btn.activeReceive=true;
            }
            else if(btn!=null&&checkValid(going,btn)&&btn.value==null&&checkSides(btn,going)) {
                btn.img.setBackgroundColor(Color.RED);
                btn.activeReceive=true;
           }
        }

    }
    public boolean checkSides(myImageButton btn,aDice going) {
        Log.d("Checking","checking sides");

        myImageButton top = getTop(btn);
        myImageButton bottom = getBottom(btn);
        myImageButton left = getLeft(btn);
        myImageButton right = getRight(btn);

        if (top != null)
            if (top.value != null)
                if (top.value.color.equals(going.color) || top.value.curValue == going.curValue)
                    return false;
        if (bottom != null)
            if (bottom.value != null)
                if (bottom.value.color.equals(going.color) || bottom.value.curValue == going.curValue)
                    return false;
        if (left != null)
            if (left.value != null)
                if (left.value.color.equals(going.color) || left.value.curValue == going.curValue)
                    return false;
        if (right != null)
            if (right.value != null)
                if (right.value.color.equals(going.color) || right.value.curValue == going.curValue)
                    return false;

        return true;

    }
    public void doActivating(aDice going){
        ArrayList<myImageButton> buttons=able();

        for(int x=0;x<buttons.size();x++) {
            myImageButton cur = buttons.get(x);
            changeBack(getRight(cur),false,going,round);
            changeBack(getLeft(cur),false,going,round);
            changeBack(getTop(cur),false,going,round);
            changeBack(getBottom(cur),false,going,round);
            changeBack(getLeftBottom(cur),false,going,round);
            changeBack(getLeftTop(cur),false,going,round);
            changeBack(getRightBottom(cur),false,going,round);
            changeBack(getRightTop(cur),false,going,round);
        }
    }
    public void deactivateAll(){
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 5; y++) {
                myImageButton btn = windBtns[x][y];
                btn.img.setBackgroundColor(Color.TRANSPARENT);
                btn.activeReceive=false;
            }
        }
    }
    public ArrayList<myImageButton> able() {
        ArrayList<myImageButton> arr = new ArrayList();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 5; y++) {
                myImageButton btn = windBtns[x][y];
                if (btn.value != null)
                    arr.add(btn);
            }
        }
        return arr;
    }

    public boolean checkValidSec(aDice toPlace,myImageButton toGet){
        if(toGet.value==null) {
            if(toGet.activeReceive) {
                if (toGet.color.equals("white"))
                    return true;
                if (toGet.greyValue > 0) {
                    if (toGet.greyValue == toPlace.curValue)
                        return true;
                } else if (toPlace.color.equals(toGet.color))
                    return true;
            }
        }
        return false;
    }
    public boolean checkValid(aDice toPlace,myImageButton toGet){

        if(toGet.value==null) {
            if (toGet.color.equals("white"))
                return true;
            if (toGet.greyValue > 0) {
                if (toGet.greyValue == toPlace.curValue)
                    return true;
            } else if (toPlace.color.equals(toGet.color))
                return true;
            return false;
        }
        return false;
    }
    private myImageButton getLeft(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(y>=1)
            return windBtns[x][y-1];

        return null;
    }
    private myImageButton getLeftTop(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(y>=1&&x>1)
            return windBtns[x-1][y-1];

        return null;
    }
    private myImageButton getLeftBottom(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(y>=1&&x<=2)
            return windBtns[x+1][y-1];

        return null;
    }
    private myImageButton getRight(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(y<=3)
            return windBtns[x][y+1];

        return null;

    }
    private myImageButton getRightTop(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(y<=3&&x>=1)
            return windBtns[x-1][y+1];

        return null;

    }
    private myImageButton getRightBottom(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(x<=2&&y<=3)
            return windBtns[x+1][y+1];

        return null;

    }
    private myImageButton getTop(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(x>=1)
            return windBtns[x-1][y];
        return null;
    }
    private  myImageButton getBottom(myImageButton thebutton){
        Pair pos=getPosition(thebutton);
        int x=(int)pos.first;
        int y=(int)pos.second;

        if(x<=2)
            return windBtns[x+1][y];

        return null;
    }
    public Pair getPosition(myImageButton thebutton){
        for(int x=0;x<4;x++){
            for(int y=0;y<5;y++){
                Log.d("x,y",+x+","+y);
                myImageButton cur=windBtns[x][y];
                if(thebutton.img.getId()==cur.img.getId())
                    return new Pair<>(x,y);
            }
        }
        return null;
    }
}
