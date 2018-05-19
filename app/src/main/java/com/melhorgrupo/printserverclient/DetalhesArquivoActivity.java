package com.melhorgrupo.printserverclient;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviado;

public class DetalhesArquivoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
