package za.ac.vhuthu.sagrada;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread {
    private long time;
    private final int fps = 20;
    private boolean toRun = false;
    private MovementView movementView;
    private SurfaceHolder surfaceHolder;
    int identifier;

    // UpdateThread constructor: The main purpose of this constructor is
    // to populate the surfaceHolder variable which will eventually be
    // used to provide a reference of the Canvas.
    public UpdateThread(MovementView rMovementView, int value) {
        movementView = rMovementView;
        surfaceHolder = movementView.getHolder();
        identifier=value;
    }


    // This method serves one simple, but essential purpose: to give the
    // thread permission to run or not to run.
    public void setRunning(boolean run) {
        toRun = run;
    }

    // This is the main method of the Thread. The code in this method
    // dictates what is done with each 'tick' of the thread.
    // This is the list of tasks it performs:
    // 1) Check if it has permission to run.
    // 2) If so, check if the required time has passed to keep in line with the
    // FPS (frames per second) value.
    // 3) If so, set the canvas to empty.
    // 4) Get a reference to the canvas and lock it to prepare for drawing.
    // 5) Update the physics of the ball.
    // 6) Draw the ball in the new position.
    // 7) If it is safe to do so, lock and update the canvas.
    @Override
    @SuppressLint("WrongCall")
    public void run() {


        Canvas c;
        while (toRun) {

            long cTime = System.currentTimeMillis();

            if ((cTime - time) <= (1000 / fps)) {

                c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    if (identifier == 0) {
                        movementView.updatePhysics();
                    }
                    if (identifier == 1) {
                        movementView.updatePhysics2();
                    }
                    movementView.onDraw(c);


                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
            time = cTime;
        }
    }

}
