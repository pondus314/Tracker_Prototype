package com.example.trackerprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class LoggingActivity extends AppCompatActivity {

    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSION_CODE = 1;

    private boolean hasPermissions(Activity activity) {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions(Activity activity) {
        if (!hasPermissions(activity)) {
            ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS, PERMISSION_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        TextView textView = findViewById(R.id.log_data_text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        requestPermissions(this);

//        startService(new Intent(this, LoggerService.class));
    }

}
