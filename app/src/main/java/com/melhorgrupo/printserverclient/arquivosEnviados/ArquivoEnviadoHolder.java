package com.melhorgrupo.printserverclient.arquivosEnviados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.melhorgrupo.printserverclient.R;

public class ArquivoEnviadoHolder extends RecyclerView.ViewHolder {

    public TextView nomeArquivo;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public ArquivoEnviadoHolder(View itemView) {
        super(itemView);
        nomeArquivo = (TextView) itemView.findViewById(R.id.nomeArquivo);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
