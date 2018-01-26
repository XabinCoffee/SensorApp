package rxabin.eu.sensorapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rxabin.eu.sensorapp.obj.Ball;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senGyroscope;
    private List<Ball> balls = new ArrayList<>();
    private TextView X_value, Y_value, Z_value, numberOfBalls, X_speed, Y_speed;
    private Button add, remove, toggleRandPos;
    public int width, height;
    public boolean randPos, debug;
    private RelativeLayout debug_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout mLayout = findViewById(R.id.main);
        randPos = false;
        debug = false;

        X_value = findViewById(R.id.textX);
        Y_value = findViewById(R.id.textY);
        Z_value = findViewById(R.id.textZ);
        numberOfBalls = findViewById(R.id.balls);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        toggleRandPos = findViewById(R.id.toggleRandomPosition);
        X_speed = findViewById(R.id.X_speed);
        Y_speed = findViewById(R.id.Y_speed);
        debug_info = findViewById(R.id.debug_info);
        debug_info.setVisibility(View.GONE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
        }


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_NORMAL);


        BallView ballView = new BallView(this);

        ballView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        mLayout.addView(ballView);


        numberOfBalls.setText("Balls: " + balls.size());

        add.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.DKGRAY, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.LTGRAY};

                for (int i = 0; i<100; i++){
                    int rnd = new Random().nextInt(colors.length);
                    int size  = new Random().nextInt(150) + 1;

                    if (randPos) {
                        int randomHeigth = new Random().nextInt(height);
                        int randomWidth = new Random().nextInt(width);
                        balls.add(new Ball(randomWidth,randomHeigth,size,colors[rnd]));
                    }else{
                        balls.add(new Ball(100,100,size,colors[rnd]));
                    }
                    numberOfBalls.setText("Balls: " + balls.size());
                }
                return true;
            }
        });


        remove.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                balls.clear();
                numberOfBalls.setText("no balls :V");
                return true;
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_GRAVITY) {

            for (Ball ball : balls){
                ball.setX_speed((float) (event.values[0]*-ball.getMultiplier()),event.values[2]);
                ball.setY_speed((float) (event.values[1]*ball.getMultiplier()),event.values[2]);
            }


            if (!balls.isEmpty()) {
                X_speed.setText("X: " + String.format(java.util.Locale.US, "%.5f",balls.get(0).getX_speed()));
                Y_speed.setText("Y: " + String.format(java.util.Locale.US, "%.5f",balls.get(0).getY_speed()));
            }


            X_value.setText("X: " + String.format(java.util.Locale.US, "%.5f",event.values[0]));
            Y_value.setText("Y: " + String.format(java.util.Locale.US, "%.5f",event.values[1]));
            Z_value.setText("Z: " + String.format(java.util.Locale.US, "%.5f",event.values[2]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void addBall(View v){
        int[] colors = {Color.BLACK, Color.BLUE, Color.RED, Color.DKGRAY, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.LTGRAY};
        int rnd = new Random().nextInt(colors.length);

        Random rn = new Random();
        int size = rn.nextInt(150) + 1;
        if (randPos) {
            int randomHeigth = new Random().nextInt(height);
            int randomWidth = new Random().nextInt(width);
            balls.add(new Ball(randomWidth,randomHeigth,size,colors[rnd]));
        }else{
            balls.add(new Ball(100,100,size,colors[rnd]));
        }
        numberOfBalls.setText("Balls: " + balls.size());
    }


    public void removeBall(View v){
        if (!balls.isEmpty())
        balls.remove(balls.size()-1);

        numberOfBalls.setText("Balls: " + balls.size());
    }

    public void toggleRandPos(View v){

        if (randPos){
            toggleRandPos.setText("RND POS 0");
            randPos = !randPos;
        } else {
            toggleRandPos.setText("RND POS 1");
            randPos = !randPos;
        }

    }


    public void toggleDebugInfo(View v){
        if (debug){
            debug = !debug;
            debug_info.setVisibility(View.GONE);
        }else{
            debug = !debug;
            debug_info.setVisibility(View.VISIBLE);
        }
    }

    public class BallView extends View {

        public BallView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }
        public BallView(Context context) {
            super(context);
            init();
        }
        private void init(){
            //Add a new ball to the view
            balls.add(new Ball(300,300,100, Color.RED));
            /*balls.add(new Ball(100,75, 50, Color.BLUE));
            balls.add(new Ball(100,75, 20, Color.MAGENTA));
            balls.add(new Ball(100,75, 75, Color.GREEN));
            balls.add(new Ball(100,75, 60, Color.YELLOW));
            balls.add(new Ball(100,75, 40, Color.GREEN));
            balls.add(new Ball(100,75, 140, Color.BLACK));*/
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            width = canvas.getWidth();
            height = canvas.getHeight();
            for(Ball ball : balls){
                ball.move(canvas);
                canvas.drawOval(ball.oval,ball.paint);
            }
            invalidate();
        }
    }

}
