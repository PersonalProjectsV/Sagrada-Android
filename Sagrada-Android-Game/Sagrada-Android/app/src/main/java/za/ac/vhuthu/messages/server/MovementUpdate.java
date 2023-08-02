package za.ac.vhuthu.messages.server;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.sagrada.GameClient;
import za.ac.vhuthu.sagrada.GameController;
import za.ac.vhuthu.sagrada.aDice;

import java.util.ArrayList;

public class MovementUpdate extends Message<GameClient> {
    ArrayList<aDice> movements;

    public MovementUpdate(ArrayList<aDice> movement) {
        movements = movement;
    }

    @Override
    public void apply(GameClient client) {
        if(!(GameClient.client.myhandl.equals("John"))){
            GameController.theController.doChanges(movements);
            //Log.d("movementUpdate","movement update received");
        }
    }
}
