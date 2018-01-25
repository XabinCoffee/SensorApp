package rxabin.eu.sensorapp.obj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by xabinrodriguez on 25/1/18.
 */

public class Ball{
    public float x,y,size;
    public float x_speed,y_speed;
    public float weight;
    public int speed = 10;
    public Paint paint;
    public RectF oval;

    public Ball(int x, int y, int size, int color){
        this.x = x;
        this.y = y;
        this.weight = 1;
        this.x_speed=0;
        this.y_speed=0;
        this.size = size;
        this.paint = new Paint();
        this.paint.setColor(color);
    }

    public void move(Canvas canvas) {
        this.x += x_speed;
        this.y += y_speed;
        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2);

        //Are we boinging?
        Rect bounds = new Rect();
        this.oval.roundOut(bounds); //Storing int bounds

        //BOING!
        if(!canvas.getClipBounds().contains(bounds)){
            if(this.x-size<0 || this.x+size > canvas.getWidth()){
               x_speed = x_speed*-1;
            }
            if(this.y-size<0 || this.y+size > canvas.getHeight()){
                y_speed = y_speed*-1;
            }
        }
    }


    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getX_speed() {
        return x_speed;
    }

    public void setX_speed(float x_speed, float z_grav) {
        this.x_speed = (float) ((this.x_speed/1.02) + (x_speed / z_grav));
    }

    public float getY_speed() {
        return y_speed;
    }

    public void setY_speed(float y_speed, float z_grav) {
        this.y_speed = (float) ((this.y_speed/1.02) + (y_speed / z_grav));
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}