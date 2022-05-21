package com.banzaraktsaeva.trueconf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.Semaphore;


public class MainActivity extends AppCompatActivity implements Runnable{

    TextView textView;
    MyAnimation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        myAnimation = new MyAnimation(MainActivity.this, textView);

        //View главной активити привязывется к ConstraintLayout по id
        View mainActivity = findViewById(R.id.screen);

        //OnTouchListener "прослушивает" весь экран MainActivity
        mainActivity.setOnTouchListener((view, motionEvent) -> {
            view.performClick();

            if (myAnimation.isAlive()){
                myAnimation.interrupt();
            }

            //При отпускании нажатия textView перемещается в координаты x, y и меняет цвет
            if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL){
                //run(motionEvent);
                MovingThread movingThread = new MovingThread(MainActivity.this, motionEvent, textView);
                movingThread.start();
            }

            return true;
        });
    }

    public void moveMethod(MotionEvent motionEvent){
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        //if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            textView.setX(x);
            textView.setY(y);

            //Цвет меняется в зависимости от языка системы
            textView.setTextColor(ContextCompat.getColor(this, R.color.move));

            /*try {
                //myAnimation.wait(5000);
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            myAnimation.start();

            //animationMethod();
        //}
    }

    public synchronized Animation animationMethod(){
        //Анимация движения вниз/вверх
        Animation downAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.down_translate);

        /*TranslateAnimation.AnimationListener downAnimationListener = new TranslateAnimation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        };

        downAnimation.setAnimationListener(downAnimationListener);*/

        textView.startAnimation(downAnimation);

        return downAnimation;
    }


    public void run(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();


        //if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
        textView.setX(x);
        textView.setY(y);

        //Цвет меняется в зависимости от языка системы
        textView.setTextColor(ContextCompat.getColor(this, R.color.move));

        myAnimation.start();

    }

    @Override
    public void run() {

    }
}

class MovingThread extends Thread{
    MotionEvent motionEvent;
    TextView textView;
    Context context;

    public MovingThread(Context context, MotionEvent motionEvent, TextView textView) {
        this.motionEvent = motionEvent;
        this.textView = textView;
        this.context = context;
    }

    @Override
    public synchronized void start() {
        super.start();

    }

    @Override
    public void run() {
        super.run();
        float x = motionEvent.getX();
        float y = motionEvent.getY();


        //if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
        textView.setX(x);
        textView.setY(y);

        //Цвет меняется в зависимости от языка системы
        textView.setTextColor(ContextCompat.getColor(context, R.color.move));

        try {
            Thread.sleep(5000);
            MyAnimation myAnimation = new MyAnimation(context, textView);
            myAnimation.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

class MyAnimation extends Thread {
    Context context;
    View view;

    MyAnimation(Context context, View view){
        this.context = context;
        this.view = view;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        notify();
    }

    @Override
    public synchronized void start() {

        super.start();
       /*try {
            Thread.sleep(5000);
            super.start();
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run() {
        Animation downAnimation = AnimationUtils.loadAnimation(context, R.anim.down_translate);
        view.startAnimation(downAnimation);
    }
}

