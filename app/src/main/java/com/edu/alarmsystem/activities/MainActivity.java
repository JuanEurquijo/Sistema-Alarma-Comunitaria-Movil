package com.edu.alarmsystem.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.ActivityMainBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    SensorsFragment sensorsFragment = new SensorsFragment();
    HousesFragment housesFragment = new HousesFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),homeFragment).commit();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),homeFragment).commit();
                    return true;
                case R.id.sensors:
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),sensorsFragment).commit();
                    return true;
                case R.id.houses:
                    getSupportFragmentManager().beginTransaction().replace(binding.container.getId(),housesFragment).commit();
                    return true;
            }
            return false;
        });
    }
}