package com.melhorgrupo.printserverclient;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviado;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviadoAdapter;
import com.melhorgrupo.printserverclient.arquivosEnviados.ArquivoEnviadoDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArquivosEnviadosFragment extends Fragment {

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
        final View view = inflater.inflate(R.layout.fragment_arquivos_enviados, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FormArquivoEnviadoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FORMULARIO);
//                view.findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
//                view.findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
//                view.findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });

        configurarRecycler(view);

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
        adapter = new ArquivoEnviadoAdapter(dao.retornarTodos());

        recyclerView.setAdapter(adapter);
        // Configurar um separador entre linhas
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ArquivosEnviadosFrag","request code!" + requestCode);
        Log.d("ArquivosEnviadosFrag","result code!" + resultCode);
        if (requestCode == REQUEST_CODE_FORMULARIO) {
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

//
//                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
//                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
//                } else {
//                    Snackbar.make(v, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
        }
    }
}
