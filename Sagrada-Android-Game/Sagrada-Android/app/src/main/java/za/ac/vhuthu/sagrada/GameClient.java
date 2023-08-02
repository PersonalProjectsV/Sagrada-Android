package za.ac.vhuthu.sagrada;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import za.ac.vhuthu.messages.client.PlayerJoined;
import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.messages.MessageReceiver;
import za.ac.vhuthu.messages.client.myName;

 public class GameClient implements Serializable {
    private String serverAddress;

    TextView printer;
    private  ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    Socket connection;

    private static BlockingQueue<Message> outgoingMessages;
    private MessageReceiver messageReceiver;

    // Thread that reads messages from the server.
    private Thread readThread;

    // Thread that writes messages to the server.
    private Thread writeThread;

    public ArrayList positions;
    String gameID;

    public String myhandl="";

    Message myName;

    Activity theAct;
    public static GameClient client;
    public GameClient(MessageReceiver messageReceiver,TextView printer,Activity theAct) {
        super();
        outgoingMessages = new LinkedBlockingQueue<>();
        this.messageReceiver = messageReceiver;
       // Log.d("new","new one made");
        this.theAct=theAct;
        client=this;
    }
    public void writeOn(String string){
        theAct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                printer.setText(printer.getText()+string);
            }
        });
    }
    public static void send(Message message) {
        try {
            outgoingMessages.put(message);
        } catch (InterruptedException e) {
            //.out.println("" + ": Read.Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void connect(String serverAddress, String handle) {
        Log.i("ChatClient", "Connecting to " + serverAddress + "...");

        // Cache info.
        this.serverAddress = serverAddress;
        // Details about the client.

        // Start the read thread (which establishes a connection.
        Log.i("ChatClient", "Starting Read Loop thread...");

        readThread = new ReadThread();
        readThread.start();

        myhandl=handle;
        // Send the setHandle message.
        Log.i("ChatClient", "Queuing SetHandle(" + handle + ")");
        //send(new SetHandle(handle));
        myName=new myName(handle);
        try {
            PlayerJoined c = new PlayerJoined();
            c.handle = myhandl;
            outgoingMessages.put(c);
            //outgoingMessages.put(myName);
        }
        catch (Exception ex){ex.printStackTrace();}
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            Log.i("ChatClient", "Started Read Loop thread...");

            readThread = this;

            try {
                // Connect to server (if can).
                connection = new Socket(serverAddress, 5050);
                Log.i("ChatClient", "Connected to " + serverAddress + "...");

                // Obtain I/O streams.
                // Possible GOTCHA: the order of obtaining the I/O streams in the server and
                // client is flipped. Needed due to synchronising the OBJECT streams on both ends.
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                Log.i("ChatClient", "Obtained I/O stream...");

                // Create and start the write thread.
                Log.i("ChatClient", "Starting Write Loop thread...");
                writeThread = new WriteThread();
                writeThread.start();

                // Go into read message loop.
                Log.i("ChatClient", "Starting Read Loop...");
                Message msg;

                boolean itsfalse = true;
                do {
                    // Read message from server.
                    msg = (Message) in.readObject();
                    // If Message Receiver given, pass it the message.
                    if (messageReceiver != null) messageReceiver.messageReceived(msg);
                } while (itsfalse);

                // Done, so close connection.
                connection.close();
                Log.i("ChatClient", "Closed connection...");


            } catch (Exception e) {
                Log.e("ChatClient", "Exception: " + e.getMessage());

            } finally {
                readThread = null;
                if (writeThread != null) writeThread.interrupt();
            }
        }
    }
    private class WriteThread extends Thread {
        @Override
        public void run() {
            Log.i("ChatClient", "Started Write Loop thread...");

            try {
                // Check outgoing messages and send.
                while (true) {
                    out.reset();
                    // Dequeue message to send. Take blocks until something to send.
                    Message msg = outgoingMessages.take();

                    //out=new ObjectOutputStream(connection.getOutputStream());
                    out.writeObject(msg);
                    out.flush();



                    Log.i("ChatClient", "<< " + msg);
                }
            } catch (Exception e) {
                writeThread = null;
            }
        }
    }
}

