package com.example.jeongyeonkim.funfuncar;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.btn_login);

        login.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent1);

            }
        }) ;

    }//onCreate 끝남.


    public void Sign_up(View view) {
        //Intent intent = new Intent(getApplicationContext(), Sign_UpActivity.class);
        Intent intent = new Intent(MainActivity.this, Sign_UpActivity.class);
        startActivity(intent);
    }
}








