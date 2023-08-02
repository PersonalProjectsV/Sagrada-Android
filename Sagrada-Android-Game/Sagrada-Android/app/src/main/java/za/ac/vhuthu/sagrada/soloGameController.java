package za.ac.vhuthu.sagrada;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class soloGameController {
    MovementView mv;
    ConstraintLayout holder;
    Button rollDie;
    Activity act;
    String myColor;
    TextView roundView;
    SagradaWindow myWindow;

    int round=1;
    public soloGameController(Activity act,ConstraintLayout holder){

        holder.setId(View.generateViewId());
        this.holder=holder;
        this.act=act;
        rollDie=act.findViewById(R.id.play);

        act.findViewById(R.id.skip).setAlpha(0);
        act.findViewById(R.id.skip).setClickable(false);
        rollDie.setOnClickListener(view->playfunc(rollDie));
        generatMyColor();

        roundView=act.findViewById(R.id.round);
        roundView.setText("round "+round);
        Button skip=act.findViewById(R.id.skip);
        skip.setOnClickListener(view->skip(skip));
    }
    public void playfunc(Button btn) {
        mv = new MovementView(act, holder, false, true, round, this,myWindow);
        mv.setId(View.generateViewId());
        if (holder != null) {
            holder.addView(mv);
            btn.setAlpha(0);
            btn.setClickable(false);
            //btn.setVisibility(View.GONE);

        }
        roundView.setVisibility(View.GONE);

    }
    //randomly allocate a color to the player
    public void generatMyColor(){
        int x=getRandomNumber(1,5);
        if(x==1)
            myColor="red";
        if(x==2)
            myColor="green";
        if(x==3)
            myColor="blue";
        if(x==4)
            myColor="purple";
        if(x==5)
            myColor="yellow";
    }
    public void skip(Button btn){
        holder.removeView(mv);
        mv=null;
        rollDie.setAlpha(1);
        rollDie.setClickable(true);
        btn.setAlpha(0);
        btn.setClickable(false);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public void roundEnded(SagradaWindow window){
        holder.removeView(mv);
        rollDie.setAlpha(1);
        rollDie.setClickable(true);
        roundView.setVisibility(View.VISIBLE);
        if(round==10)
        {
            scoreCalculator calculator=new scoreCalculator(window,myColor);
            roundView.setText("Final socre "+calculator.doScore());
            rollDie.setVisibility(View.GONE);
            return;
        }
        round++;
        roundView.setText("round "+round);
        myWindow=window;

    }

}
