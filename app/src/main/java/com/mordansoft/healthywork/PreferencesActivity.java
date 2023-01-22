package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mordansoft.healthywork.databinding.ActivityPreferencesBinding;

public class PreferencesActivity extends AppCompatActivity {

    private ActivityPreferencesBinding binding;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        init();
        updateUi();
    }

    public void init(){
        binding = ActivityPreferencesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSavePreferences.setOnClickListener(btnBackListener);
        binding.btnPreferencesBack.setOnClickListener(btnBackListener);
        preferences = Preferences.getPreferencesFromFile(this);
        binding.btnPreferencesMinusCount.setOnClickListener(btnMinusListener);
        binding.btnPreferencesPlusCount.setOnClickListener(btnPlusListener);
    }
    private void updateUi(){
        MordanSoftLogger.addLog("PreferencesActivity.updateUi START");
        binding.tvPreferencesCountdown.setText(String.valueOf(preferences.getCountdown()));

        int period = preferences.getPeriod();
        if (String.valueOf(period).equals(getString(R.string.activity_preferences_half_hour))){
            binding.swPreferencesPeriod.setChecked(false);
        } else if (String.valueOf(period).equals(getString(R.string.activity_preferences_hour))){
            binding.swPreferencesPeriod.setChecked(true);
        } else {
            MordanSoftLogger.addLog("PreferencesActivity  updateUi period error: ",
                                    'e');
        }
        MordanSoftLogger.addLog("PreferencesActivity.updateUi END");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }



    /********** Listeners **********/

    View.OnClickListener btnBackListener = v -> goBack();

    View.OnClickListener btnPlusListener = v -> {
        changeCount(5);
    };
    View.OnClickListener btnMinusListener = v -> changeCount(-5);


    /* ****** ! Listeners **********/

    private void changeCount(int delta){
        int count = Integer.parseInt(String.valueOf(binding.tvPreferencesCountdown.getText())) + delta;
        if (count > 59){
            count = 0;
        } else if (count < 0){
            count = 55;
        }
        binding.tvPreferencesCountdown.setText(String.valueOf(count) );
    }
    private void saveState(){ //todo checks
        try {
            String countdownStr = String.valueOf(binding.tvPreferencesCountdown.getText());
            int countdownInt = Integer.parseInt(countdownStr);
            preferences.setCountdown(this,countdownInt);
            if (binding.swPreferencesPeriod.isChecked()){
                preferences.setPeriod(this,
                        Integer.parseInt(getString(R.string.activity_preferences_hour)));
            } else {
                preferences.setPeriod(this,
                        Integer.parseInt(getString(R.string.activity_preferences_half_hour)));
            }
            Toast.makeText(this, getString(R.string.activity_preferences_save_message), Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            MordanSoftLogger.addLog("btnSaveListener - " + e, 'e');
        }
    }


    private void goBack() {
        saveState();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }


}