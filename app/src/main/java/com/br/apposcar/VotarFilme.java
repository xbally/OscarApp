package com.br.apposcar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class VotarFilme extends AppCompatActivity {
    private ProgressDialog mProgressBar;

    private int dialogStatus = 0;

    private Handler manipulador = new Handler();

    private long contagemFake = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar_filme);

        mProgressBar = new ProgressDialog(VotarFilme.this);
        mProgressBar.setCancelable(false);
      //   mProgressBar.setTitle(getString(R.string.lbl_loading));
        mProgressBar.setMessage("iniciando");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mProgressBar.show();

        ProcessData p = new ProcessData();
        p.execute(10);


    }

        public class ProcessData extends AsyncTask<Integer, String, String> {

            @Override
            protected String doInBackground(Integer... integers) {

                int progress = 0;
                int total = integers[0];

                while (progress <= total) {

                    try {

                        Thread.sleep(2000); // 2 segundos

                    } catch(InterruptedException e) {

                    }

                    String m = progress % 2 == 0 ? "Carregando arquivos" : "carregando Imagens";

                    // exibimos o progresso
                    this.publishProgress(String.valueOf(progress), String.valueOf(total), m);
                    progress++;
                }

                return "DONE";

            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);

                Float progress = Float.valueOf(values[0]);
                Float total = Float.valueOf(values[1]);

                String message = values[2];

                mProgressBar.setProgress((int) ((progress / total) * 100));
                mProgressBar.setMessage(message);

                // se os valores são iguais, termianos nosso processamento
                if (values[0].equals(values[1])) {
                    // removemos a dialog
                    mProgressBar.cancel();
                }
            }


        }
}
