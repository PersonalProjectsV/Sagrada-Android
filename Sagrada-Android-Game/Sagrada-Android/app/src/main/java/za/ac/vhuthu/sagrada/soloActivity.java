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

public class soloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo);

        ConstraintLayout cs = this.findViewById(R.id.cl);
        read();
        soloGameController x=new soloGameController(this,cs);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public void read() {
        int num=getRandomNumber(1,6);
        String whichGrid="";

        if(num==1)
            whichGrid="one";
        if(num==2)
            whichGrid="two";
        if(num==3)
            whichGrid="three";
        if(num==4)
            whichGrid="four";
        if(num==5)
            whichGrid="five";
        if(num==6)
            whichGrid="six";
        BufferedReader reader = null;
        try {
            String gridString="window_"+whichGrid+".txt";
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(gridString)));

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
