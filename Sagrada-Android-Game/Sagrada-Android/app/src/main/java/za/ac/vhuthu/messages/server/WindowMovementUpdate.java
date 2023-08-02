package za.ac.vhuthu.messages.server;

import android.util.Log;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.sagrada.GameClient;
import za.ac.vhuthu.sagrada.GameController;
import za.ac.vhuthu.sagrada.aDice;

import java.util.ArrayList;

public class WindowMovementUpdate extends Message<GameClient> {
    ArrayList<aDice> movements;

    public WindowMovementUpdate(ArrayList<aDice> movement) {
        movements = movement;
    }

    @Override
    public void apply(GameClient client) {
        if(!(GameClient.client.myhandl.equals("John"))){
            GameController.theController.doChanges(movements,-1);
            Log.d("movementUpdateWindow","movement change received");
        }
    }
}
