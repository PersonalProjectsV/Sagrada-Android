package za.ac.vhuthu.messages;

import java.io.Serializable;


/**
 * messages will be sent between server and clients to notify what to do next
 * @param <C>
 */
public abstract class Message<C> implements Serializable {
    private static final long serialVersionUID = 999L;


    /**
     * this method is used to apply the logic of a message
     * @param context
     */
    public void apply(C context) {}
}

