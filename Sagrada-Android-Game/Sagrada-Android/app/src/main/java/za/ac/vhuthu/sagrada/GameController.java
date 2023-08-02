package za.ac.vhuthu.sagrada;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import za.ac.vhuthu.messages.server.Joined;


public class GameController {
    ConstraintLayout cs;

    int round;

    public ArrayList<aDice> curGame;
    public static ArrayList<Pair<ArrayList<aDice>,MovementView>> players;
    MovementView currentlyActive;

    boolean wasSet=false;


    public static GameController theController;
    Activity act;
    String thisplayerHandle;
    public GameController(ConstraintLayout cs, Activity act,String handle)
    {
        this.cs=cs;
        players=new ArrayList<>();
        this.act=act;
        theController=this;
        thisplayerHandle=handle;
        round=1;
        MovementView x=new MovementView(act,cs,false,false,round,null,null);
        currentlyActive=x;
        x.setId(View.generateViewId());
        cs.addView(x);
        players.add(new Pair<>(new ArrayList<>(),x));

        addPlayers();
        if(!(thisplayerHandle.equals("John"))) {
            changeShowing("John");
        }

    }

    public void changeShowing(String identifier) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentlyActive != null)
                    cs.removeView(currentlyActive);

                for (int x = 0; x < players.size(); x++) {
                    if (identifier.equals(players.get(x).second.handle)) {
                        cs.addView(players.get(x).second);
                    }
                }
            }
        });
    }
    public void doChanges(ArrayList<aDice> change) {
        if (!wasSet) {
            for (int x = 0; x < players.size(); x++) {
                if (!("John".equals(players.get(x).second.handle))) {
                    if(players.get(x).second.getParent()==null) {
                        cs.addView(players.get(x).second);
                        curGame = players.get(x).second.positions;
                        currentlyActive = players.get(x).second;
                    }
                }
            }
            //wasSet=true;
        }
        if(currentlyActive!=null) {
            currentlyActive.tActive=true;
            currentlyActive.fillTheArr(change);

        }
    }

    public void placingEnded(myImageButton img){
        if(img.value!=null&&currentlyActive!=null){
            currentlyActive.doPlaceEnding(img);
        }
        if(img!=null) {
            //System.out.println("...................................received placeEnded...........................................");
            if (img.place != null)
                //System.out.println(myBtn.place);
                if(img.value!=null){
                    Log.d("dd","...................................received placeEnded...........................................");
                //System.out.println("...................................received placeEnded...........................................");
                    //System.out.println("the button has a value...................................................................");
                    Log.d("ddd","the button has a value...................................................................");
                }
        }
    }


    public void doChanges(ArrayList<aDice> change,int val) {
        if (!wasSet) {
            for (int x = 0; x < players.size(); x++) {
                if (!("John".equals(players.get(x).second.handle))) {
                    if(players.get(x).second.getParent()==null) {
                        cs.addView(players.get(x).second);
                        curGame = players.get(x).second.positions;
                        currentlyActive = players.get(x).second;
                    }
                }
            }
            //wasSet=true;
        }

        if(currentlyActive!=null) {
            currentlyActive.fillTheArr(change);
            currentlyActive.tActive=true;
            if(currentlyActive.tActive)
                Log.d("",".....................................................................................");
            Log.d("pars2","second message sent");
        }
    }

    public void addPlayers(){
        ArrayList arr=ConnectToServer.players;

        for(int x=0;x<arr.size();x++) {
            Joined joined = (Joined) arr.get(x);
            MovementView mv = new MovementView(act, cs,false,false,round,null,null);
            mv.setId(View.generateViewId());

            mv.handle = joined.handle;
            players.add(new Pair<>(mv.positions,mv));
            Log.d("addedPLayer", "Player Added " + players.size());


        }


    }

}
