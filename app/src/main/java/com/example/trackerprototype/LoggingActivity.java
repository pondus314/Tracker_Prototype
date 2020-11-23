package com.example.trackerprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class LoggingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        TextView textView = findViewById(R.id.log_data_text);
        textView.setMovementMethod(new ScrollingMovementMethod());


//        startService(new Intent(this, LoggerService.class));
    }

}
