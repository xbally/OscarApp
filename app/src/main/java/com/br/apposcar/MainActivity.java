package com.br.apposcar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity  extends AppCompatActivity {
    public static final String REQUEST_TAG = "MainVolleyActivity";
    private EditText login;
    private EditText senha;
    private Button lButton;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        lButton = (Button) findViewById(R.id.bLogin);
    }

    public void login(View v) {
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://192.168.15.5:8083/OscarServidor/UserServlet";
        try {
            JSONObject postparams = new JSONObject();
            postparams.put("login", login.getText().toString());
            postparams.put("password", senha.getText().toString());
            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                    .POST, url,
                    postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String login = ((JSONObject) response).getString("login");

                        if ( login == null || login.equals("false") ) {
                            String message = ((JSONObject) response).getString("message");
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Erro ao logar");
                            alertDialog.setMessage(message);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            String name = ((JSONObject) response).getString("name");
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            intent.putExtra("nameUser", name);
                            startActivity(intent);
                        }

                    } catch ( JSONException e) {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Erro transformar JSON");
                        alertDialog.setMessage(e.getMessage());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Erro da requisição");
                    alertDialog.setMessage(error.getMessage());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
            jsonRequest.setTag(REQUEST_TAG);
            mQueue.add(jsonRequest);
        } catch ( JSONException e ) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Erro do JSON");
            alertDialog.setMessage(e.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Fechar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    public void Registrar(View view) {
        Intent intent = new Intent(getApplicationContext(), Registrar.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mQueue != null ) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }
}
