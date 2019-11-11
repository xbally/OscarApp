package com.br.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void ValidarLogin(View view){
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
    }
}
