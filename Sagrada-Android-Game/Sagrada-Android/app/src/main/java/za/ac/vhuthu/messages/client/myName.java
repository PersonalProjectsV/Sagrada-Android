package za.ac.vhuthu.messages.client;

import za.ac.vhuthu.messages.Message;

public class myName extends Message {
    private static final long serialVersionUID = 101L;
    String myName;

    public myName(String myName){
        this.myName=myName;
    }
    @Override
    public String toString(){
        return myName;
    }
}
