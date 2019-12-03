package com.doitutpl.doit.ui.acercade;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doitutpl.doit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcercaDeFragment extends Fragment {

    TextView tvAcercaDe;

    public AcercaDeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_acerca_de, container, false);

        tvAcercaDe = root.findViewById(R.id.tvAcercaDe);


        return root;
    }

}
