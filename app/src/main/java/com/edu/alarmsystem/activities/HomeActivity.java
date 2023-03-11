package com.edu.alarmsystem.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.os.Bundle;

import com.edu.alarmsystem.R;
import com.edu.alarmsystem.databinding.ActivityHomeBinding;
import com.edu.alarmsystem.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    HomeFragment homeFragment = new HomeFragment();
    SensorsFragment sensorsFragment = new SensorsFragment();
    HousesFragment housesFragment = new HousesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
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


        binding.buttonMenu.setOnClickListener(view-> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navMenu.setNavigationItemSelectedListener(this::onOptionsItemSelected);
    }
}