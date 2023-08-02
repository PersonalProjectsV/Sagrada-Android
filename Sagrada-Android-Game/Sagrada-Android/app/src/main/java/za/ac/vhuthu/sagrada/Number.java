package za.ac.vhuthu.sagrada;

import java.io.Serializable;

public class Number implements Serializable {
    private static final long serialVersionUID = 151L;

    public int num;

    public Number(int x){
        num=x;
    }
}
