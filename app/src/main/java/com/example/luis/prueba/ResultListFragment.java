package com.example.luis.prueba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultListFragment extends Fragment implements MainActivity.SendDataFromService {


    public ResultListFragment() {
        // Required empty public constructor
    }

    @Override
    public void sendData(JSONObject jsonResponse) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_list, container, false);
    }

}
