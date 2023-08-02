package za.ac.vhuthu.messages.client;

import java.util.ArrayList;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.sagrada.GameClient;
import za.ac.vhuthu.sagrada.SagradaWindow;
import za.ac.vhuthu.sagrada.aDice;

/**
 * this message tells the server the position of the dice being placed in the window
 */
public class WindowMovememt extends Message<GameClient> {


    private static final long serialVersionUID = 106L;
    int x;
    int y;
    float xv;
    float yv;
    float targetX, targetY;
    float startX, startY;

    boolean ended;

    boolean targets;
    int speed;

    boolean brought;
    int butPoY;
    int butposX;

    boolean begin=false;


    int round;
    SagradaWindow windoww;
    ArrayList<aDice> movements;

    public WindowMovememt(ArrayList movements){
        this.movements=movements;
    }

}
