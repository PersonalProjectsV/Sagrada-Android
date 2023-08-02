package za.ac.vhuthu.sagrada;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

import za.ac.vhuthu.messages.client.Movement;
import za.ac.vhuthu.messages.client.WindowMovememt;
import za.ac.vhuthu.messages.client.placeEnded;


/**
 * this is the view where the dice will be moving
 */
public class MovementView extends SurfaceView implements SurfaceHolder.Callback {
    public ArrayList<aDice> positions = new ArrayList<>();

    boolean active;
    int gr = 0;
    private final int size = 100;
    private int width;
    private int height;

    UpdateThread updateThread;

    Activity cx;
    float xv = 600;
    float yv = 600;
    float targetX = 0, targetY = 100;
    float startX = xv, startY = yv;

    boolean ended = false;

    boolean targets = false;
    int speed = 10;

    boolean brought = false;
    int butPoY;
    int butposX;

    boolean begin = false;

    public  boolean tActive=false;

    SagradaWindow windoww;
    ConstraintLayout holder;

    UpdateThread moveToWindow;

    public String handle;

    soloGameController controller;
    int round;
    boolean solo;
    aDice selected;
    public MovementView(Activity context, ConstraintLayout holder,boolean active,boolean solo,int round,soloGameController controller,SagradaWindow window) {
        super(context);
        this.active=active;
        getHolder().addCallback(this);
        cx = context;
        this.holder=holder;
        TableLayout wind = context.findViewById(R.id.window);
        if(round==1) {
            windoww = new SagradaWindow(wind);
        }
        else windoww=window;
        Paint xxx = new Paint();
        xxx.setColor(Color.RED);
        int velocity = 5;


        this.round=round;
        this.solo=solo;
        handle="";

        for (int x = 0; x < 9; x++) {
            aDice newone = new aDice(0, 0, 0, 0, velocity, velocity, "", null);
            positions.add(newone);
        }
        fillArr();
        this.controller=controller;
    }


    /**
     * get a specific dice form the pool
     * @param btn
     * @return
     */
    public aDice getDice(Button btn) {
        for (int x = 0; x < positions.size(); x++) {
            Button cur = positions.get(x).myBtn;
            if (cur.getId() == btn.getId()) {
                return positions.get(x);

            }

        }
        return null;
    }

    /**
     * draw die on canvas
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Paint xxx = new Paint();
        xxx.setColor(Color.RED);
        canvas.drawColor(Color.GREEN);

        for (int x = 0; x < positions.size(); x++) {
            aDice cur = positions.get(x);
            String myString = cur.valueString;
            if (!ended) {
                int id = cx.getResources().getIdentifier(myString, "drawable", cx.getPackageName());
                Drawable d = getResources().getDrawable(id, null);
                d.setBounds(cur.positionn.getLeft(), cur.positionn.getTop(), cur.positionn.getRight(), cur.positionn.getBottom());
                //send message of a movement
                d.draw(canvas);
                if (!solo)
                    GameClient.send(new Movement(positions));
            } else {
                int id = cx.getResources().getIdentifier(myString, "drawable", cx.getPackageName());
                Drawable d = getResources().getDrawable(id, null);
                d.setBounds(cur.start.x - 100, cur.start.y - 100, cur.start.x, cur.start.y);
                d.draw(canvas);
                //sent messsage of a movement
                if (!solo)
                    GameClient.send(new WindowMovememt(positions));
            }
        }
    }

    public  void fillTheArr(ArrayList<aDice> ht) {

        if (ht.size() > 8 && positions.size() > 8) {

            for (int x = 0; x < positions.size(); x++) {
                aDice cur = positions.get(x);
                aDice curr = ht.get(x);
                cur.set(curr);
            }
        }

    }

    public void doOrderedButtons() {
        cx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button prev=null;
                ConstraintLayout cs=holder;

                for(int i=0;i<9;i++) {
                    ConstraintSet set = new ConstraintSet();
                    set.clone(cs);

                    aDice cur=positions.get(i);
                    Button button = new Button(cx);
                    button.setText(" ");
                    button.setId(View.generateViewId());
                    cs.addView(button);

                    button.setWidth(100);
                    button.setHeight(100);
                    if(i==0) {
                        set.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
                        set.connect(button.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
                    }
                    else
                    {
                        set.connect(button.getId(), ConstraintSet.TOP, prev.getId(), ConstraintSet.BOTTOM, 0);
                        set.connect(button.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
                    }
                    set.constrainHeight(button.getId(), 100);
                    set.constrainWidth(button.getId(), 100);
                    set.applyTo(cs);
                    button.setAlpha(0);
                    //button.bringToFront();
                    button.setOnClickListener(vieww -> {
                        deselectDices();
                        aDice dice=getDice(button);

                        if(dice!=null) {
                            dice.select();
                            selected=dice;
                            windoww.deactivateAll();
                            windoww.activate(dice, round);
                            //round++;
                        }
                    });

                    prev=button;

                    cur.myBtn=button;
                }
            }
        });
    }
    public void doPlaceEnding(myImageButton btn){
        if(btn.value!=null){
            for(int x=0;x<4;x++){
                for(int y=0;y<5;y++){
                    myImageButton cur=windoww.windBtns[x][y];
                    if(cur!=null){
                        if(cur.place.equals(btn.place)){
                            String curr = btn.value.valueString;
                            int id = cx.getResources().getIdentifier(curr, "drawable", cx.getPackageName());
                            cur.img.post(new Runnable() {
                                public void run() {
                                    cur.img.setImageResource(id);
                                }
                            });
                            break;

                        }
                    }
                }
            }
        }
    }
    //animate dice moving from pool to the window
    public void updatePhysics2() {
        //if(!tActive) {
        TableLayout tbl = windoww.theWindow;
        Log.d("update2", "i am running");
        //Log.d("table", tbl.getX() + " " + tbl.getY());


        if (!solo)
            GameClient.send(new placeEnded(getSelectedImg()));
        //Log.d("sec", "Second thread running");
        int[] location = new int[2];
        if (getSelectedImg() != null) {
            getSelectedImg().img.getLocationInWindow(location);
            butposX = location[0];
            butPoY = location[1];

            //Log.d("windowX",butposX+" and "+secondClick.img.getX());
            //Log.d("windowY",butPoY+" and "+secondClick.img.getY());

            aDice cur = getSelectedDice();
            if (cur != null) {
                if (!solo)
                    GameClient.send(new placeEnded(getSelectedImg()));
                if (!begin) {
                    cur.start.set(cur.positionn.getRight(), cur.positionn.getTop());
                    cur.target.set(((int) Math.floor(getSelectedImg().img.getX() + 180)), butPoY);
                    cur.speed = 3;
                    begin = true;
                }

                if (cur.start.y >= tbl.getY() && cur.start.x <= (tbl.getWidth() - tbl.getX())) {
                    if (!solo)
                        GameClient.send(new placeEnded(getSelectedImg()));
                    cur.speed = 0;
                    getSelectedImg().value = cur;
                    String curr = cur.valueString;
                    int id = cx.getResources().getIdentifier(curr, "drawable", cx.getPackageName());
                    getSelectedImg().img.post(new Runnable() {
                        public void run() {
                            getSelectedImg().img.setImageResource(id);
                            if(solo) {
                                cx.findViewById(R.id.play).setAlpha(1);
                                cx.findViewById(R.id.play).setClickable(true);
                                moveToWindow.setRunning(false);
                                controller.roundEnded(windoww);
                            }
                        }
                    });

                    windoww.deactivateAll();
                    positions.remove(cur);
                }
                float deltaX = cur.target.x - cur.start.x;
                float deltaY = cur.target.y - cur.start.y;
                double angle = Math.atan2(deltaY, deltaX);
                cur.start.x += ((int) Math.floor(cur.speed * Math.cos(angle)));//Using cos
                cur.start.y += ((int) Math.floor(cur.speed * Math.sin(angle)));//or sin
            }
        }
        //  }


    }

    /**
     * animate the die moving around the screen updating their positions,speed
     */
    public void updatePhysics() {

        //if the die havent stopped moving
        if (!ended) {
            float deltaX = targetX - startX;
            float deltaY = targetY - startY;
            double angle = Math.atan2(deltaY, deltaX);

            xv += speed * Math.cos(angle);//Using cos
            yv += speed * Math.sin(angle);//or sin

            for (int x = 0; x < positions.size(); x++) {
                aDice cur = positions.get(x);
                cur.positionn.setRight(cur.positionn.getRight() + cur.xVelocity);
                cur.positionn.setLeft(cur.positionn.getLeft() + cur.xVelocity);
                cur.positionn.setTop(cur.positionn.getTop() + cur.yVelocity);
                cur.positionn.setBottom(cur.positionn.getBottom() + cur.yVelocity);

                ArrayList<Integer> ch = new ArrayList<>();
                for (int y = 0; y < positions.size(); y++) {
                    if (y != x) {
                        aDice other = positions.get(y);
                        Rect cc = new Rect(cur.positionn.getLeft(), cur.positionn.getTop(), cur.positionn.getRight(), cur.positionn.getBottom());
                        Rect ccc = new Rect(other.positionn.getLeft(), other.positionn.getTop(), other.positionn.getRight(), other.positionn.getBottom());
                        if (cc.intersect(ccc) && !cc.contains(ccc)) {
                            if (cur.xVelocity != 0 && cur.yVelocity != 0) {
                                cur.doValue();
                                cur.xVelocity *= -1;
                                cur.yVelocity *= -1;
                                if (cur.yVelocity < 0)
                                    cur.yVelocity++;
                                else cur.yVelocity--;

                                if (cur.xVelocity < 0)
                                    cur.xVelocity++;
                                else cur.xVelocity--;
                            } else {
                                cur.xVelocity *= -1;
                                cur.yVelocity *= -1;
                            }
                            ch.add(y);
                        }
                        Log.d("velocity", cur.xVelocity + "");
                    }
                }
                if (cur.positionn.getTop() < 0 || cur.positionn.getBottom() > height) {
                    cur.doValue();
                    // the ball has hit the top or the bottom of the canvas
                    if (cur.positionn.getTop() < 0) {
                        // the ball has hit the top of the canvas
                        cur.positionn.setTop(0);
                        cur.positionn.setBottom(size);
                    } else {
                        // the ball has hit the bottom of the canvas
                        cur.positionn.setTop(height - size);
                        cur.positionn.setBottom(height);
                    }

                    // reverse the y direction of the ball
                    cur.yVelocity *= -1;
                    if (cur.yVelocity < 0)
                        cur.yVelocity++;
                    else cur.yVelocity--;

                    if (cur.xVelocity < 0)
                        cur.xVelocity++;
                    else cur.xVelocity--;
                }
                if (cur.positionn.getLeft() < 0 || cur.positionn.getRight() > width) {
                    cur.doValue();
                    // the ball has hit the sides of the canvas
                    if (cur.positionn.getLeft() < 0) {
                        // the ball has hit the left of the canvas
                        cur.positionn.setLeft(0);
                        cur.positionn.setRight(size);
                    } else {
                        // the ball has hit the right of the canvas
                        cur.positionn.setLeft(width - size);
                        cur.positionn.setRight(width);
                    }
                    // reverse the x direction of the ball
                    cur.xVelocity *= -1;
                    if (cur.yVelocity < 0)
                        cur.yVelocity++;
                    else cur.yVelocity--;

                    if (cur.xVelocity < 0)
                        cur.xVelocity++;
                    else cur.xVelocity--;
                }

                if (allStopped(positions)) {
                    ended = true;
                }
            }
            gr++;
        } else {
            if (!targets) {
                sort(positions);
                doOrderedButtons();
                int start = this.getTop() + 100;
                for (int x = 0; x < positions.size(); x++) {
                    aDice cur = positions.get(x);
                    cur.start.set(cur.positionn.getRight(), cur.positionn.getTop());
                    cur.target.set(width, start);
                    start += 100;
                }
                targets = true;
            }
            for (int x = 0; x < positions.size(); x++) {

                aDice cur = positions.get(x);
                float deltaX = cur.target.x - cur.start.x;
                float deltaY = cur.target.y - cur.start.y;
                double angle = Math.atan2(deltaY, deltaX);
                cur.start.x += ((int) Math.floor(cur.speed * Math.cos(angle)));//Using cos
                cur.start.y += ((int) Math.floor(cur.speed * Math.sin(angle)));//or sin
                //Log.d("width", width + "");
                cur.positionn.setTop(cur.start.y);
                cur.positionn.setRight(cur.start.x);
                if (cur.start.x >= width - 6) {
                    cur.speed = 0;
                    cur.start.y = cur.target.y;
                    cur.start.x = cur.target.x;
                }
                if (allStopped2(positions)) {
                    updateThread.setRunning(false);

                    if(!solo) {
                        if (tActive) {
                            UpdateThread updateThread = new UpdateThread(this, 1);
                            updateThread.setRunning(true);
                            updateThread.start();
                        }
                    }
                    if (!brought) {
                        TableLayout cons = windoww.theWindow;
                        cx.runOnUiThread(new Runnable() {
                            public void run() {
                                cons.bringToFront();
                                brought = true;

                                if(solo) {
                                    cx.findViewById(R.id.skip).setAlpha(1);

                                    cx.findViewById(R.id.skip).setClickable(true);
                                }
                            }
                        });


                    }
                }
            }
        }
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        targetX = width;

        targetY = surfaceFrame.top;

        for (int x = 0; x < positions.size(); x++) {
            aDice cur = positions.get(x);
            cur.positionn.setLeft(getRandomNumber((width / width) + size, width));
            cur.positionn.setTop(getRandomNumber(height / height + size, height));
            cur.positionn.setRight(cur.positionn.getLeft() + size);
            cur.positionn.setBottom(cur.positionn.getTop() + size);
            cur.speed = 3;
        }
        try {
            Thread.sleep(50);
        } catch (Exception io) {
            io.printStackTrace();
        }
        updateThread = new UpdateThread(this,0);
        updateThread.setRunning(true);
        updateThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * deals with closing the surface of the canvas
     * @param holder
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        updateThread.setRunning(false);
        while (retry) {
            try {
                updateThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * sort the die after rolling
     * @param arr
     */
    public void sort(ArrayList<aDice> arr) {
        int n = arr.size();
        for (int i = 1; i < n; ++i) {
            aDice cur = arr.get(i);
            int j = i - 1;
            while (j >= 0 && arr.get(j).curValue > cur.curValue) {
                arr.set(j + 1, arr.get(j));
                j = j - 1;
            }
            arr.set(j + 1, cur);
        }

    }
    public boolean allStopped(ArrayList<aDice> dice) {

        for (int x = 0; x < dice.size(); x++) {
            if (dice.get(x).xVelocity != 0 || dice.get(x).yVelocity != 0)
                return false;
        }
        return true;

    }

    public boolean allStopped2(ArrayList<aDice> dice) {

        for (int x = 0; x < dice.size(); x++) {
            if (dice.get(x).speed != 0)
                return false;
        }
        return true;

    }
    public  void fillArr() {
        if(round==1) {
            for (int x = 1; x <= 4; x++) {
                for (int y = 1; y <= 5; y++) {
                    ImageButton btnBoardTile = cx.findViewById(getResources().getIdentifier("r" + "" + x + "c" + "" + y, "id", cx.getPackageName()));
                    myImageButton btn = new myImageButton(btnBoardTile);
                    btn.place = x + "," + y;
                    btn.color = (String) btnBoardTile.getTag();
                    String theTag = (String) btn.img.getTag();
                    if (theTag.startsWith("grey")) {
                        String[] thee = theTag.split("_");

                        String number = thee[1];
                        if (number.equals("one"))
                            btn.greyValue = 1;
                        if (number.equals("two"))
                            btn.greyValue = 2;
                        if (number.equals("three"))
                            btn.greyValue = 3;
                        if (number.equals("four"))
                            btn.greyValue = 4;
                        if (number.equals("five"))
                            btn.greyValue = 5;
                        if (number.equals("six"))
                            btn.greyValue = 6;
                    }

                    windoww.windBtns[x - 1][y - 1] = btn;

                    btn.img.setOnClickListener(view -> {
                        deselectAll();
                        btn.selected = true;

                        if (getSelectedDice() != null) {
                            if (windoww.checkValidSec(getSelectedDice(), getSelectedImg())) {
                                begin = false;
                                moveToWindow = new UpdateThread(this, 1);
                                moveToWindow.setRunning(true);
                                moveToWindow.start();
                                if (solo) {
                                    cx.findViewById(R.id.skip).setVisibility(GONE);
                                }
                            }
                        }
                    });
                }
            }
        }
        else {
            for (int x = 1; x <= 4; x++) {
                for (int y = 1; y <= 5; y++) {



                    myImageButton btn= windoww.windBtns[x - 1][y - 1];

                    btn.img.setOnClickListener(view -> {
                        deselectAll();
                        btn.selected = true;

                        if (getSelectedDice() != null) {
                            if (windoww.checkValidSec(getSelectedDice(), getSelectedImg())) {
                                begin = false;
                                moveToWindow = new UpdateThread(this, 1);
                                moveToWindow.setRunning(true);
                                moveToWindow.start();
                                if (solo) {
                                    cx.findViewById(R.id.skip).setVisibility(GONE);
                                }
                            }
                        }
                    });
                }
            }
        }
    }
    public aDice getSelectedDice(){
        for(int x=0;x<positions.size();x++){
            aDice cur=positions.get(x);
            if(cur.selected) {
                return cur;
            }
        }
        return null;
    }
    public myImageButton getSelectedImg(){
        for(int x=0;x<4;x++){
            for(int y=0;y<5;y++) {
                myImageButton cur =windoww.windBtns[x][y];
                if(cur.selected) {
                    return cur;
                }
            }
        }
        return null;
    }
    public void deselectDices(){
        for(int x=0;x<positions.size();x++){
            aDice cur=positions.get(x);
            cur.selected=false;
        }
    }
    public void deselectAll(){
        for(int x=0;x<4;x++){
            for(int y=0;y<5;y++) {
                myImageButton cur =windoww.windBtns[x][y];
                cur.selected=false;
            }
        }
    }
}
