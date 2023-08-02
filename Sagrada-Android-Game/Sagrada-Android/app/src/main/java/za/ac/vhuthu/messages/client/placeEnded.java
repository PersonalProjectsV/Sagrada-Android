package za.ac.vhuthu.messages.client;

import za.ac.vhuthu.sagrada.myImageButton;

import za.ac.vhuthu.messages.Message;

public class placeEnded extends Message {
    private static final long serialVersionUID = 131L;
    myImageButton myBtn;
    public placeEnded(myImageButton btn)
    {
        this.myBtn=btn;
    }
}
