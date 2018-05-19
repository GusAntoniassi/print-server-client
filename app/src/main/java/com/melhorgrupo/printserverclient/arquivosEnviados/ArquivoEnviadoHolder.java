package com.melhorgrupo.printserverclient.arquivosEnviados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.melhorgrupo.printserverclient.R;

public class ArquivoEnviadoHolder extends RecyclerView.ViewHolder {

    public TextView nomeArquivo;
    public TextView enviadoEm;
    public TextView ultimaImpressao;
    public ImageButton btnMenu;

    public ArquivoEnviadoHolder(View itemView) {
        super(itemView);
        nomeArquivo = (TextView) itemView.findViewById(R.id.nomeArquivo);
        enviadoEm = (TextView) itemView.findViewById(R.id.enviadoEm);
        ultimaImpressao = (TextView) itemView.findViewById(R.id.ultimaImpressao);

        btnMenu = (ImageButton) itemView.findViewById(R.id.btnMenu);
    }
}
