package za.ac.vhuthu.sagrada;

import java.io.Serializable;

public class dicePos implements Serializable {
    private static final long serialVersionUID = 122L;
    private int left;
    private int top;
    private int right;
    private int bottom;
    public dicePos(int left,int top,int right,int bottom){
        this.left=left;
        this.right=right;
        this.top=top;
        this.bottom=bottom;
    }

    public void set(dicePos dicePos){
        left=dicePos.getLeft();
        top=dicePos.getTop();
        right=dicePos.getRight();
        bottom=dicePos.getBottom();
    }
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }
}
