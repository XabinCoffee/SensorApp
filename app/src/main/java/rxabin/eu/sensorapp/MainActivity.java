package rxabin.eu.sensorapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import rxabin.eu.sensorapp.obj.Ball;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senGyroscope;
    private List<Ball> balls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout mLayout = findViewById(R.id.main);

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
            balls.get(1).setX_speed((float) (event.values[0]*-4));
            balls.get(1).setY_speed((float) (event.values[1]*4));

            balls.get(0).setX_speed(event.values[0]*-2);
            balls.get(0).setY_speed(event.values[1]*2);

            balls.get(2).setX_speed(event.values[0]*-1);
            balls.get(2).setY_speed(event.values[1]*1);

            balls.get(3).setX_speed(event.values[0]*-3);
            balls.get(3).setY_speed(event.values[1]*3);

            //speed.setText("X: "+ balls.get(1).getX_speed()+ ", Y:" +balls.get(1).getY_speed());
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
