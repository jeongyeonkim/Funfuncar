package com.example.jeongyeonkim.funfuncar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IntentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser_2);

        Intent intent = new Intent(IntentActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
}
