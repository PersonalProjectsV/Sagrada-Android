package za.ac.vhuthu.sagrada;

import android.widget.ImageButton;

import java.io.Serializable;

public class myImageButton implements Serializable {

    private static final long serialVersionUID = 105L;
    public transient ImageButton img;
    public boolean selected=false;
    public String color;
    public int greyValue=-1;
    public aDice value=null;
    boolean activeReceive=false;
    public String place="";

    public myImageButton(ImageButton imageButton){
        this.img=imageButton;
    }

    public void setImg(ImageButton img) {
        this.img = img;
    }

    public ImageButton getImg() {
        return img;
    }

    public boolean isSelected(){
        return selected;
    }

    public void select(){
        selected=true;
    }
    public void deselect(){
        selected=false;
    }

    public aDice getValue() {
        return value;
    }

    public int getGreyValue() {
        return greyValue;
    }

    public String getColor() {
        return color;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGreyValue(int greyValue) {
        this.greyValue = greyValue;
    }

    public void setValue(aDice value) {
        this.value = value;
    }
}
