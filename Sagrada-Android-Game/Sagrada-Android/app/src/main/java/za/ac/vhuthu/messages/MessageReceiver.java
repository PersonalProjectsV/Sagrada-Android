package za.ac.vhuthu.messages;


/**
 *
 * notifies when a message was received
 */
@FunctionalInterface
public interface MessageReceiver {
    void messageReceived(Message message);
}
