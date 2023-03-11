package com.edu.alarmsystem.activities;

import android.content.Intent;
import android.os.Bundle;

import com.edu.alarmsystem.databinding.ActivityLoginBinding;

public class LoginActivity extends Activity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.signUpButton.setOnClickListener(v->startActivity(new Intent(this, SignUpActivity.class)));
        binding.buttonLogin.setOnClickListener(v->startActivity(new Intent(this,HomeActivity.class)));
    }
}