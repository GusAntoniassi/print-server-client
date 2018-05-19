package com.melhorgrupo.printserverclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {
    public final static String TAG = "SobreFrag";

    public SobreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Pegar o title do strings.xml
        String title = getResources().getString(R.string.title_sobre);
        getActivity().setTitle(title);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sobre, container, false);
    }

}
