package com.example.lenovo.gymclub;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CoachesFragment extends ListFragment {

    String[] coaches = {"Sam", "Tommy", "Gimmy", "zzzzzz", "ffffff"};
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    private boolean added = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        if (!added) {
            //MAP
            HashMap<String, String> map;
            for (int i = 0; i < coaches.length; i++) {
                map = new HashMap<String, String>();
                map.put("Coaches", coaches[i]);
                map.put("Icon", Integer.toString(R.drawable.ic_profile));
                data.add(map);
            }
            String[] from = {"Icon", "Coaches"};
            int[] to = {R.id.coachicon, R.id.coachname};
            //ADAPTER
            adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.coach_item, from, to);
            setListAdapter(adapter);
            added = true;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), data.get(pos).get("Coaches"), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
