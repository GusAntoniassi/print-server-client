package com.melhorgrupo.printserverclient.arquivosEnviados;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.melhorgrupo.printserverclient.ArquivosEnviadosFragment;
import com.melhorgrupo.printserverclient.DetalhesArquivoActivity;
import com.melhorgrupo.printserverclient.FormArquivoEnviadoActivity;
import com.melhorgrupo.printserverclient.R;

import java.util.List;

public class ArquivoEnviadoAdapter extends RecyclerView.Adapter<ArquivoEnviadoHolder> {
    private final List<ArquivoEnviado> arquivosEnviados;
    private Context context;

    public ArquivoEnviadoAdapter(List<ArquivoEnviado> arquivosEnviados, Context context) {
        this.arquivosEnviados = arquivosEnviados;
        this.context = context;
    }

    public void atualizarArquivoEnviado(ArquivoEnviado arquivoEnviado) {
        arquivosEnviados.set(arquivosEnviados.indexOf(arquivoEnviado), arquivoEnviado);
        notifyItemChanged(arquivosEnviados.indexOf(arquivoEnviado));
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
        final ArquivoEnviado arquivoEnviado = arquivosEnviados.get(posicao);
        Resources res = context.getResources();

        holder.nomeArquivo.setText(res.getString(R.string.format_nome_arquivo, arquivoEnviado.getNome()));
        holder.enviadoEm.setText(res.getString(R.string.format_enviado_em, arquivoEnviado.getEnviadoEm()));
        if (arquivoEnviado.getUltimaImpressao().isEmpty()) {
            holder.ultimaImpressao.setVisibility(View.GONE);
        } else {
            holder.ultimaImpressao.setText(res.getString(R.string.format_ultima_impressao, arquivoEnviado.getUltimaImpressao()));
            holder.ultimaImpressao.setVisibility(View.VISIBLE);
        }
        holder.btnMenu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenu(v, arquivoEnviado);
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

    private void mostrarMenu(final View view, final ArquivoEnviado arquivoEnviado) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.arquivos_enviados_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.arquivo_ver_detalhes:
                        mostrarDetalhesArquivo(view, arquivoEnviado);
                        break;
                    case R.id.arquivo_abrir:
                        Toast.makeText(view.getContext(), "Implementação futura",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.arquivo_imprimir:
                        Toast.makeText(view.getContext(), "Implementação futura",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.arquivo_editar:
                        editarArquivo(view, arquivoEnviado);
                        break;
                    case R.id.arquivo_excluir:
                        excluirArquivo(view, arquivoEnviado);
                        break;
                    default:
                        return false;
                }

                // Se chegou até aqui, o evento foi consumido
                return true;
            }
        });
    }

    private void mostrarDetalhesArquivo(final View view, final ArquivoEnviado arquivoEnviado) {
        Activity activity = getActivity(view);
        Intent intent = new Intent(activity.getBaseContext(), DetalhesArquivoActivity.class);
        intent.putExtra("arquivoEnviado", arquivoEnviado);
        activity.startActivity(intent);
    }

    private void editarArquivo(final View view, final ArquivoEnviado arquivoEnviado) {
        Activity activity = getActivity(view);
        Intent intent = new Intent(activity.getBaseContext(), FormArquivoEnviadoActivity.class);
        intent.putExtra("arquivoEnviado", arquivoEnviado);
        activity.startActivityForResult(intent, ArquivosEnviadosFragment.REQUEST_CODE_FORMULARIO);
    }

    private void excluirArquivo(final View view, final ArquivoEnviado arquivoEnviado) {
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
}
