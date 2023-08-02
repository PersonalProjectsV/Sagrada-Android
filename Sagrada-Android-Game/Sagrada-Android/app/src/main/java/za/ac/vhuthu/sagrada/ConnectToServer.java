package za.ac.vhuthu.sagrada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import za.ac.vhuthu.messages.Message;
import za.ac.vhuthu.messages.server.Joined;
import za.ac.vhuthu.messages.server.MovementUpdate;
import za.ac.vhuthu.messages.server.StartGame;
import za.ac.vhuthu.messages.server.WindowMovementUpdate;

public class ConnectToServer extends AppCompatActivity {
    GameClient client;
    TextView wait;
    static public ArrayList players=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_server);


        TextView ipAd=findViewById(R.id.ipAddress);
        TextView name=findViewById(R.id.name);
        Button connect=findViewById(R.id.connect);

        TextView myName=findViewById(R.id.myName);


        ConstraintLayout land=findViewById(R.id.land);
        ConstraintLayout waiting=findViewById(R.id.wait);
        waiting.setAlpha(0);

        wait=findViewById(R.id.waiting);
        wait.setMovementMethod(new ScrollingMovementMethod());

        boolean hasPermission =
                checkSelfPermission(Manifest.permission.INTERNET) ==
                        PackageManager.PERMISSION_GRANTED;
        Log.d("internet","Internet Permission = " + hasPermission);


        //if playing a multiplayer game connect to the server
        connect.setOnClickListener(view->{

            String serverAddress = ipAd.getText().toString();
            String handle =  name.getText().toString();

            myName.setText(handle);


            Log.i("ChatClient", "Connecting to " + serverAddress + " as " + handle);
            client = new GameClient(
                    message -> runOnUiThread(
                            () -> onMessageReceived(message)
                    ),wait,this);

            client.connect(serverAddress, handle);

            GameClient.client.myhandl=handle;


            land.setAlpha(0);
            waiting.setAlpha(1);


        });

        Button solo=findViewById(R.id.soloGame);
        solo.setOnClickListener(view->{
            Intent myIntent = new Intent(this, soloActivity.class);
            startActivity(myIntent);
        });
    }

    /**
     * if a message is received,apply logic based on the message
     * @param message
     */
    public void onMessageReceived(Message message) {
        if(message instanceof Joined)
        {
            wait.setText(wait.getText()+"\n"+message.toString());
            message.apply(GameClient.client);
        }
        if(message instanceof StartGame){
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
        }
        if(message instanceof MovementUpdate){
            message.apply(GameClient.client);
        }
        if(message instanceof WindowMovementUpdate)
            message.apply(GameClient.client);
    }
}
