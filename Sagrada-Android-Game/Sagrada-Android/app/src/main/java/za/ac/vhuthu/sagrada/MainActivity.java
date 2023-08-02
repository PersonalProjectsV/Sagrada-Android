package za.ac.vhuthu.sagrada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConstraintLayout cs = this.findViewById(R.id.cl);


        read();
        GameController controller=new GameController(cs,this,GameClient.client.myhandl);

        //Button btn = findViewById(R.id.play);
        //btn.setOnClickListener(view -> {

     //       controller.addPlayers();

            //MovementView f = new MovementView(this,cs,true,false,1);
            //f.setId(View.generateViewId());
            //f.handle=GameClient.client.myhandl;
            //controller.players.add(new Pair<>(f.positions,f));
            //controller.currentlyActive=f;
            //controller.changeShowing(GameClient.client.myhandl);

     //   });
    }
    public void read() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("window_three.txt")));

            // do reading, usually loop until end of file reading
            int row=1;
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String[] cur = mLine.split(",");

                for (int x = 0; x < cur.length; x++) {

                    String curr = cur[x];
                    ImageButton btnBoardTile = findViewById(getResources().getIdentifier("r"+""+row+ "c" + "" + (x + 1), "id", getPackageName()));
                    Context context = btnBoardTile.getContext();
                    int id = context.getResources().getIdentifier(curr, "drawable", context.getPackageName());
                    btnBoardTile.setImageResource(id);
                    btnBoardTile.setTag(curr);
                }
                Log.d("a line", mLine);
                row++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }
}
