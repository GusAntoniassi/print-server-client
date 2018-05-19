package com.melhorgrupo.printserverclient.arquivosEnviados;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melhorgrupo.printserverclient.ArquivosEnviadosFragment;
import com.melhorgrupo.printserverclient.FormArquivoEnviadoActivity;
import com.melhorgrupo.printserverclient.R;

import java.util.List;

public class ArquivoEnviadoAdapter extends RecyclerView.Adapter<ArquivoEnviadoHolder> {
    private final List<ArquivoEnviado> arquivosEnviados;

    public ArquivoEnviadoAdapter(List<ArquivoEnviado> arquivosEnviados) {
        this.arquivosEnviados = arquivosEnviados;
    }

    public void atualizarArquivoEnviado(ArquivoEnviado arquivoEnviado) {
        arquivosEnviados.set(arquivosEnviados.indexOf(arquivoEnviado), arquivoEnviado);
        notifyItemChanged(arquivosEnviados.indexOf(arquivosEnviados));
    }

    public void adicionarArquivoEnviado(ArquivoEnviado arquivoEnviado) {
        arquivosEnviados.add(arquivoEnviado);
        notifyItemInserted(getItemCount());
    }

    public void removerArquivoEnviado(ArquivoEnviado arquivoEnviado) {
        int posicao = arquivosEnviados.indexOf(arquivoEnviado);
        arquivosEnviados.remove(posicao);
        notifyItemRemoved(posicao);
    }

    @Override
    public ArquivoEnviadoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArquivoEnviadoHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_lista_arquivos_enviados, parent, false));
    }

    @Override
    public void onBindViewHolder(ArquivoEnviadoHolder holder, int posicao) {
        holder.nomeArquivo.setText(arquivosEnviados.get(posicao).getNome());
        final ArquivoEnviado arquivoEnviado = arquivosEnviados.get(posicao);

        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder
                    .setTitle("Confirmação")
                    .setMessage("Tem certeza que deseja excluir este arquivo?")
                    .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArquivoEnviadoDAO dao = new ArquivoEnviadoDAO(view.getContext());
                            boolean sucesso = dao.excluir(arquivoEnviado.getId());
                            if (sucesso) {
                                removerArquivoEnviado(arquivoEnviado);
                                Snackbar.make(view, "Registro excluído", Snackbar.LENGTH_LONG);
                            } else {
                                Snackbar.make(view, "Erro ao excluir o registro!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
            }
        });

        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = new Intent(activity.getBaseContext(), FormArquivoEnviadoActivity.class);
                intent.putExtra("arquivoEnviado", arquivoEnviado);
                activity.startActivityForResult(intent, ArquivosEnviadosFragment.REQUEST_CODE_FORMULARIO);
            }
        });
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }

            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return arquivosEnviados != null ? arquivosEnviados.size() : 0;
    }
}
