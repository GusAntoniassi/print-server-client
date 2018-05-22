package com.melhorgrupo.printserverclient;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviado;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviadoAdapter;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviadoDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArquivosEnviadosFragment extends Fragment {
    public static final String TAG = "ArquivosEnviadosFrag";

    ArquivoEnviado arquivoEditado = null;
    private ArquivoEnviadoAdapter adapter;
    private RecyclerView recyclerView;

    public static final int REQUEST_CODE_FORMULARIO = 1;

    public ArquivosEnviadosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Pegar o title do strings.xml
        String title = getResources().getString(R.string.title_arquivos_enviados);
        getActivity().setTitle(title);

        final View view = inflater.inflate(R.layout.fragment_arquivos_enviados, container, false);
        configurarRecycler(view);

        // Null Pointer Exception
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Refresh items
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(view.getContext(), "Arquivos atualizados!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void configurarRecycler(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // Configurar o gerenciador de layout para ser uma lista
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Adicionar o adapter que irá anexar objetos na lista
        ArquivoEnviadoDAO dao = new ArquivoEnviadoDAO(getContext());
        adapter = new ArquivoEnviadoAdapter(dao.retornarTodos(), getContext());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FORMULARIO && data != null) {
            Bundle extras = data.getExtras();

            if (resultCode == FormArquivoEnviadoActivity.RESULT_SUCESSO) { // Sucesso
                ArquivoEnviado arquivoEnviado = (ArquivoEnviado) extras.getSerializable("arquivoEnviado");
                if (extras.getBoolean("editou")) {
                    adapter.atualizarArquivoEnviado(arquivoEnviado);
                } else {
                    adapter.adicionarArquivoEnviado(arquivoEnviado);
                }

                Snackbar.make(getView(), "Salvou!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (resultCode == FormArquivoEnviadoActivity.RESULT_ERRO) {
                Snackbar.make(getView(), "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        }
    }
}
