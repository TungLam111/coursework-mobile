package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements AnimationView.OnSpriteClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        AnimationView animationView = new AnimationView(this);
/*        animationView.setOnSpriteClickListener(new MyAnimationView.OnSpriteClickListener() {
            @Override
            public void OnSpriteClick(View view, float x, float y, int selectedIndex) {

            }
        });*/
        animationView.setOnSpriteClickListener(this);
        setContentView(animationView);
    }

    @Override
    public void OnSpriteClick(View view, float x, float y, int selectedIndex) {

    }
}