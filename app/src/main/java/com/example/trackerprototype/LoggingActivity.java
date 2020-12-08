package com.example.trackerprototype;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoggingActivity extends AppCompatActivity {

    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    private static final int PERMISSION_CODE = 1;

//    private FileLogger mFileLogger;
//    private GnssContainer mGnssContainer;

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            boolean permissions_denied = false;
            if (grantResults.length == REQUIRED_PERMISSIONS.length) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        permissions_denied = true;
                        break;
                    }
                }
            } else {
                permissions_denied = true;
            }
            if (permissions_denied) {
                requestPermissions(this);
            }
        } else {
            System.out.println("Something weird happened: permission code inexistent");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        TextView textView = findViewById(R.id.log_data_text);
        textView.setMovementMethod(new ScrollingMovementMethod());
        requestPermissions(this);
        startService(new Intent(this, GnssLoggerService.class));
//        mFileLogger = new FileLogger(getApplicationContext());
//        mGnssContainer = new GnssContainer(getApplicationContext(), mFileLogger);
//        mGnssContainer.registerAll();
//        mFileLogger.startNewLog();
//        startService(new Intent(this, LoggerService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void stopLog(View view) {
        stopService(new Intent(this, GnssLoggerService.class));
    }

//    public void sendLog(View view) {
//        mFileLogger.close();
//    }

}
