package com.banzaraktsaeva.trueconf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Animation downAnimation;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        downAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.down_translate);

        //View главной активити привязывется к ConstraintLayout по id
        View mainActivity = findViewById(R.id.screen);

        //OnTouchListener "прослушивает" весь экран MainActivity
        mainActivity.setOnTouchListener((view, motionEvent) -> {
            view.performClick();

            float x = motionEvent.getX();
            float y = motionEvent.getY();

            //Если нажатие не по тексту, то
            if (motionEvent.getAction() == MotionEvent.ACTION_UP & x != textView.getX() & y != textView.getY()){
                //При отпускании нажатия textView перемещается в координаты x, y и меняет цвет
                moveMethod(motionEvent);
            } else {
                downAnimation.cancel();
            }

            return true;
        });
    }

    public synchronized void moveMethod(MotionEvent motionEvent){
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        textView.setX(x);
        textView.setY(y);

        //Цвет меняется в зависимости от языка системы
        textView.setTextColor(ContextCompat.getColor(this, R.color.move));

        animationMethod();
    }

    public synchronized void animationMethod(){
        //Анимация движения вниз/вверх
        downAnimation.setStartOffset(5000);

        Animation.AnimationListener downAnimationListener = new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) {
                animation.setStartOffset(0);
            }
        };

        downAnimation.setAnimationListener(downAnimationListener);

        textView.startAnimation(downAnimation);
    }
}
