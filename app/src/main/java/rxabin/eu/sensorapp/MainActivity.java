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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxabin.eu.sensorapp.obj.Ball;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senGyroscope;
    private List<Ball> balls = new ArrayList<>();
    private TextView X_value, Y_value, Z_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout mLayout = findViewById(R.id.main);

        X_value = findViewById(R.id.textX);
        Y_value = findViewById(R.id.textY);
        Z_value = findViewById(R.id.textZ);

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


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_GRAVITY) {
            balls.get(1).setX_speed((float) (event.values[0]*-3.7),event.values[2]);
            balls.get(1).setY_speed((float) (event.values[1]*3.7),event.values[2]);

            balls.get(0).setX_speed((float) (event.values[0]*-2.3),event.values[2]);
            balls.get(0).setY_speed((float) (event.values[1]*2.3),event.values[2]);

            balls.get(2).setX_speed((float) (event.values[0]*-0.7),event.values[2]);
            balls.get(2).setY_speed((float) (event.values[1]*0.7),event.values[2]);

            balls.get(3).setX_speed((float) (event.values[0]*-3.3),event.values[2]);
            balls.get(3).setY_speed((float) (event.values[1]*3.3),event.values[2]);

            balls.get(4).setX_speed(event.values[0]*-4,event.values[2]);
            balls.get(4).setY_speed(event.values[1]*4,event.values[2]);

            balls.get(5).setX_speed(event.values[0]*-2,event.values[2]);
            balls.get(5).setY_speed(event.values[1]*2,event.values[2]);

            balls.get(6).setX_speed(event.values[0]*-5,event.values[2]);
            balls.get(6).setY_speed(event.values[1]*5,event.values[2]);

            X_value.setText("X: " + event.values[0]);
            Y_value.setText("Y: " + event.values[1]);
            Z_value.setText("Z: " + event.values[2]);
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
            balls.add(new Ball(50,50,100, Color.RED));
            balls.add(new Ball(100,75, 50, Color.BLUE));
            balls.add(new Ball(100,75, 20, Color.MAGENTA));
            balls.add(new Ball(100,75, 75, Color.GREEN));
            balls.add(new Ball(100,75, 60, Color.YELLOW));
            balls.add(new Ball(100,75, 40, Color.DKGRAY));
            balls.add(new Ball(100,75, 140, Color.BLACK));
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for(Ball ball : balls){
                ball.move(canvas);
                canvas.drawOval(ball.oval,ball.paint);
            }
            invalidate();
        }
    }

}
