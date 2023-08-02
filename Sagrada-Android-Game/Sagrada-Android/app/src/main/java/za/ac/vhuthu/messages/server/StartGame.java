package za.ac.vhuthu.messages.server;

import za.ac.vhuthu.messages.Message;

public class StartGame extends Message {
    private static final long serialVersionUID = 113L;
    int gameID;
    public StartGame(int gameID)
    {
        this.gameID=gameID;
    }
}
