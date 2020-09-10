package com.example.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class Main3Activity extends AppCompatActivity {

    LottieAnimationView splash;
    ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);
        splash = findViewById(R.id.spla);
        lay = (ConstraintLayout)findViewById(R.id.lay);
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splash.cancelAnimation();
                startActivity(new Intent(Main3Activity.this , MainActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        splash.playAnimation();
        super.onResume();
    }
}
