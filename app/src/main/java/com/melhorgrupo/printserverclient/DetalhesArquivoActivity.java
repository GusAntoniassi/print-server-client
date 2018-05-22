package com.melhorgrupo.printserverclient;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviado;

public class DetalhesArquivoActivity extends AppCompatActivity {
    private final String TAG = "DetalhesArquivoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.activity_detalhes_arquivo);

        String title = getResources().getString(R.string.title_detalhes_arquivo);
        setTitle(title);

        TextView nomeArquivo = (TextView) findViewById(R.id.detalhes_nome_arquivo);
        TextView enviadoEm = (TextView) findViewById(R.id.detalhes_enviado_em);
        TextView ultimaImpressao = (TextView) findViewById(R.id.detalhes_ultima_impressao);

        ArquivoEnviado arquivoEnviado = (ArquivoEnviado) getIntent().getExtras().getSerializable("arquivoEnviado");
        Resources resources = getResources();

        nomeArquivo.setText(resources.getString(R.string.format_nome_arquivo, arquivoEnviado.getNome()));
        enviadoEm.setText(resources.getString(R.string.format_enviado_em, arquivoEnviado.getEnviadoEm()));
        ultimaImpressao.setText(resources.getString(R.string.format_ultima_impressao, arquivoEnviado.getUltimaImpressao()));
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
