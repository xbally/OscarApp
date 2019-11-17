package com.br.apposcar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

public class VotarFilme extends AppCompatActivity {
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar_filme);

        init();

        showDialogPorcess();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(),"Fim Do Processamento", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        },4000);
    }

    public void init(){
        this.progressDialog = new ProgressDialog(this);
    }

    private void showDialogPorcess(){
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Gerando");
        progressDialog.setMessage("PROCESSANDO");
        progressDialog.show();
    }


}
