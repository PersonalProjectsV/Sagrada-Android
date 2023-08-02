package za.ac.vhuthu.messages.server;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.sagrada.ConnectToServer;
import za.ac.vhuthu.sagrada.GameClient;


public class Joined extends Message<GameClient> {
    private static final long serialVersionUID = 112L;

    public int groupID;
    public String handle;

    public Joined(String handle,int groupID) {
        //this.groupName = groupName;
        this.groupID=groupID;
        this.handle = handle;
    }

    @Override
    public String toString() {
        return handle+"  joined";
    }

    @Override
    public void apply(GameClient client){
        if(!(handle.equals(client.myhandl)))
            ConnectToServer.players.add(this);
        //GameController.theController.addPlayer(handle);
    }

}
