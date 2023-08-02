package za.ac.vhuthu.messages.server;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.sagrada.GameClient;
import za.ac.vhuthu.sagrada.GameController;
import za.ac.vhuthu.sagrada.myImageButton;
public class finishedPlacing extends Message<GameClient> {
    private static final long serialVersionUID = 132L;
    myImageButton theBtn;
    public finishedPlacing(myImageButton btn){
        theBtn=btn;
    }
    @Override
    public void apply(GameClient client){
        GameController.theController.placingEnded(theBtn);
    }
}
