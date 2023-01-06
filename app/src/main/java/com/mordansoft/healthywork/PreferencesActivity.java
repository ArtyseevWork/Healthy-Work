package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    private void updateUi(){
        MordanSoftLogger.addLog("PreferencesActivity.updateUi START");
        binding.etPreferencesCountdownStart.setText(String.valueOf(preferences.getCountdown()));

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

    public void init(){
        binding = ActivityPreferencesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnPreferencesSave.setOnClickListener(btnSaveListener);
        preferences = Preferences.getPreferencesFromFile(this);
    }

    View.OnClickListener btnSaveListener = v -> {
        this.saveState(v);
    };


    private void saveState(View view){ //todo checks
        try {
            String countdownStr = String.valueOf(binding.etPreferencesCountdownStart.getText());
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


}