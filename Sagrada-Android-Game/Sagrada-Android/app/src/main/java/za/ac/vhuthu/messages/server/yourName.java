package za.ac.vhuthu.messages.server;

public class yourName {
    private static final long serialVersionUID = 111L;

    public String yourName;

    public yourName(String yourName) {
        this.yourName = yourName;
    }

    @Override
    public String toString() {
        return yourName;
    }
}
