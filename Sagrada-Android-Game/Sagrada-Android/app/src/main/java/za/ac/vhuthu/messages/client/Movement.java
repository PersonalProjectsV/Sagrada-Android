package za.ac.vhuthu.messages.client;


import java.util.ArrayList;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.sagrada.GameClient;
import za.ac.vhuthu.sagrada.SagradaWindow;
import za.ac.vhuthu.sagrada.aDice;

/**
 * This is a client message to update the server on where the dice on this clients screen is at that moment
 */
public class Movement extends Message<GameClient> {

    private static final long serialVersionUID = 103L;
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
    public Movement(ArrayList movements){
        this.movements=movements;
    }

}
