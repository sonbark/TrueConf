package com.banzaraktsaeva.trueconf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

        //View главной активити привязывется к ConstraintLayout по id
        View mainActivity = findViewById(R.id.screen);

        //OnTouchListener "прослушивает" весь экран MainActivity
        mainActivity.setOnTouchListener((view, motionEvent) -> {
            view.performClick();

            x = motionEvent.getX();
            y = motionEvent.getY();

            //При отпускании нажатия textView перемещается в координаты x, y и меняет цвет
            if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                textView.setX(x);
                textView.setY(y);

                //Цвет меняется в зависимости от языка системы
                textView.setTextColor(ContextCompat.getColor(this, R.color.move));
            }
            return true;
        });
    }
}