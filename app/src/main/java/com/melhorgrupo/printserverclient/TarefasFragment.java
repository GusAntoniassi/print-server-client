package com.melhorgrupo.printserverclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TarefasFragment extends Fragment {
    public static final String TAG = "TarefasFrag";

    public TarefasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Pegar o title do strings.xml
        String title = getResources().getString(R.string.title_tarefas);
        getActivity().setTitle(title);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tarefas, container, false);
    }
}
