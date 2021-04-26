package com.coolcats.storageappintro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.coolcats.storageappintro.util.Logger.logMessage;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private SharedPreferences preferences;

    private SharedPreferences ePreferences;

    int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logMessage("onCreate");
        textView = findViewById(R.id.main_text);

        textView.setOnClickListener(view -> {
            count++;
            textView.setText(count+"");
        });

        try {
            ePreferences = EncryptedSharedPreferences.create(
                    getPackageName(),
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            logMessage(e.getMessage());
        }
        preferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        //count = preferences.getInt("KEY", 0);
        count = ePreferences.getInt("KEY", 0);
        textView.setText(""+count);

//        if(savedInstanceState != null) {
//            logMessage("Data Restored!");
//            count = savedInstanceState.getInt("KEY");
//            textView.setText(count+"");
//        }
    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        logMessage("Data Restored!");
//        count = savedInstanceState.getInt("KEY");
//        textView.setText(count+"");
//    }
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        logMessage("onSaveInstanceState");
//        outState.putInt("KEY", count);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        logMessage("onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logMessage("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logMessage("onResume");
    }

    @Override
    protected void onDestroy() { // not called 1% of the time
        super.onDestroy();
        logMessage("onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("KEY", count);
//        editor.apply();

        //preferences.edit().putInt("KEY", count).apply();
        ePreferences.edit().putInt("KEY", count).apply();

        logMessage("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logMessage("onRestart");
    }
}