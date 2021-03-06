package com.melhorgrupo.printserverclient;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.melhorgrupo.printserverclient.R;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviado;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviadoAdapter;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviadoDAO;

public class FormArquivoEnviadoActivity extends AppCompatActivity {
    private final String TAG = "DetalhesArquivoActivity";

    ArquivoEnviado arquivoEditado = null;
    private ArquivoEnviadoAdapter adapter;
    public static final int RESULT_SUCESSO = 10; // Começa em 10 pra não dar conflito com os RESULTs da própria Activity
    public static final int RESULT_ERRO = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_arquivo_enviado);

        String title = getResources().getString(R.string.title_form_arquivo);
        setTitle(title);

        Intent intent = getIntent();
        if (intent.hasExtra("arquivoEnviado")) {
            arquivoEditado = (ArquivoEnviado) intent.getSerializableExtra("arquivoEnviado");
            EditText txtNome = (EditText) findViewById(R.id.txtNome);
            EditText txtEnviadoEm = (EditText) findViewById(R.id.txtEnviadoEm);
            EditText txtUltimaImpressao = (EditText) findViewById(R.id.txtUltimaImpressao);

            txtNome.setText(arquivoEditado.getNome());
            txtEnviadoEm.setText(arquivoEditado.getEnviadoEm());
            txtUltimaImpressao.setText(arquivoEditado.getUltimaImpressao());
        }

        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // carregar os campos
                EditText txtNome = (EditText) findViewById(R.id.txtNome);
                EditText txtEnviadoEm = (EditText) findViewById(R.id.txtEnviadoEm);
                EditText txtUltimaImpressao = (EditText) findViewById(R.id.txtUltimaImpressao);

                // pegar os valores
                String nome = txtNome.getText().toString();
                String enviadoEm = txtEnviadoEm.getText().toString();
                String ultimaImpressao = txtUltimaImpressao.getText().toString();

                ArquivoEnviadoDAO dao = new ArquivoEnviadoDAO(getBaseContext());
                boolean sucesso;
                if (arquivoEditado != null) {
                    sucesso = dao.salvar(arquivoEditado.getId(), nome, enviadoEm, ultimaImpressao);
                } else {
                    sucesso = dao.salvar(nome, enviadoEm,  ultimaImpressao);
                }

                Intent returnIntent = new Intent();

                if (sucesso) {
                    ArquivoEnviado arquivoEnviado;

                    if (arquivoEditado != null) {
                        arquivoEnviado = dao.retornarPorId(arquivoEditado.getId());
                    } else {
                        arquivoEnviado = dao.retornarUltimo();
                    }

                    returnIntent.putExtra("arquivoEnviado", arquivoEnviado);
                    returnIntent.putExtra("editou", arquivoEditado != null);
                    setResult(RESULT_SUCESSO,returnIntent);
                } else {
                    setResult(RESULT_ERRO, returnIntent);
                }

                finish();
            }
        });

        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
