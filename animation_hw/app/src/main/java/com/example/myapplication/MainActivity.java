//19125102
//Phan Đàm Tùng Lâm

// Press on each island, it will display a greeting sentence. If we press it again, this talk dialog will disappear
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AnimationView.OnSpriteClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException ignored) {
        }
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
        //Toast.makeText(this, Integer.toString(selectedIndex), Toast.LENGTH_LONG );
    }
}