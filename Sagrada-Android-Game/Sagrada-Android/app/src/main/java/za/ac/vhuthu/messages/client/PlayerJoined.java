package za.ac.vhuthu.messages.client;

import za.ac.vhuthu.messages.Message;

/**
 * this notifies the server that this player has joined the game
 */
public class PlayerJoined extends Message {
    private static final long serialVersionUID = 102L;


     public String handle;
    @Override
    public String toString(){
        return handle+"has joined the game";
    }
}
