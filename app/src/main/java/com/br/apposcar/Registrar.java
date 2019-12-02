package com.br.apposcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class Registrar extends AppCompatActivity implements Response.Listener,Response.ErrorListener, View.OnClickListener {
    public static final String REQUEST_TAG = "Registrar";
    private RequestQueue mQueue;
    private EditText edtName;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        edtName = (EditText) findViewById(R.id.name);
        edtSenha = (EditText) findViewById(R.id.senha);
        edtConfirmaSenha = (EditText) findViewById(R.id.Confirsenha);
    }

    public void registrar(){
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        final Response.Listener<JSONObject> list = this;
        final Response.ErrorListener errorListener = this;

        String usuarioTxt = edtName.getText().toString();
        String senha= edtSenha.getText().toString();
        String confirmaSenha= edtConfirmaSenha.getText().toString();
        
        if (usuarioTxt.isEmpty()) {
            Toast.makeText(this, "Usuário em branco!", Toast.LENGTH_LONG).show();
        } else if (senha.isEmpty()) {
            Toast.makeText(this, "Senha em branco!", Toast.LENGTH_LONG).show();
        } else if(!senha.equals(confirmaSenha)) {
            Toast.makeText(this, "Senha não foi confirmada corretamente.", Toast.LENGTH_LONG).show();
        } else {
            String url = getString(R.string.server_path) + "Cadastro?login=" + usuarioTxt + "&senha=" + senha;

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, new JSONObject(), list, errorListener);
            jsonRequest.setTag(REQUEST_TAG);
        }
    }

    @Override
    public void onClick(View v) {
        registrar();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        try {
            String msg = (((JSONObject) response).getString("message"));
            if (msg == null || msg.isEmpty()) {
                Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_LONG).show();
            } else if(msg.equalsIgnoreCase("Login existente")) {
                Toast.makeText(this,"Login já existente. Favor escolher outro.",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Cadastro realizado com sucesso.",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e){
            Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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