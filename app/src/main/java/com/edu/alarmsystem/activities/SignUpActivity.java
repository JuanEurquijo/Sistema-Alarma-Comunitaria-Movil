package com.edu.alarmsystem.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.alarmsystem.R;
import com.edu.alarmsystem.activities.Activity;
import com.edu.alarmsystem.databinding.ActivityLoginBinding;
import com.edu.alarmsystem.databinding.ActivitySignUpBinding;
import com.edu.alarmsystem.utils.AlertsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class SignUpActivity extends Activity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButton.setOnClickListener(v->startActivity(new Intent(this, LoginActivity.class)));
        binding.buttonSignUp.setOnClickListener(v-> {

            try {
                registerUser();
            } catch (CertificateException | KeyStoreException | KeyManagementException | IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        });
    }


    private void registerUser() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {


        InputStream caInput = getResources().openRawResource(R.raw.server);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(caInput, "Alarma123.".toCharArray());

       // crea un administrador de confianza de SSL personalizado que confía en el certificado
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        String url = "https://192.168.80.16:8443/api/auth/register";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", binding.userName.getEditText().getText());
            jsonBody.put("password", binding.password.getEditText().getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, response -> {
            new AlertsHelper().shortToast(getApplicationContext(),"Registro Exitoso");
        }, error -> new AlertsHelper().shortToast(getApplicationContext(),error.toString())){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        HurlStack hurlStack = new HurlStack(null, sslSocketFactory);
        RequestQueue requestQueue  = Volley.newRequestQueue(this,hurlStack);
        requestQueue.add(postRequest);

    }
}