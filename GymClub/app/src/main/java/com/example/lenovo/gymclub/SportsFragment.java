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

public class SportsFragment extends ListFragment {

    String[] sports = { "Archary", "Basketball", "Bowling", "Boxing", "Football", "Tennis",
            "Table Tennis", "Volleyball", "Weight Lefting" };
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    private boolean added = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        int[] icons = { R.drawable.ic_icon_archary, R.drawable.ic_icon_basketball, R.drawable.ic_icon_bowling,
            R.drawable.ic_icon_boxing, R.drawable.ic_icon_football, R.drawable.ic_icon_lontenis,
            R.drawable.ic_icon_tenis, R.drawable.ic_icon_volleyball, R.drawable.ic_icon_weight_lefting };
        if (!added) {
            //MAP
            HashMap<String, String> map;
            for (int i = 0; i < sports.length; i++) {
                map = new HashMap<String, String>();
                map.put("Sports", sports[i]);
                map.put("Icon", Integer.toString(icons[i]));
                data.add(map);
            }
            String[] from = {"Icon", "Sports"};
            int[] to = {R.id.sportsicon, R.id.sportsname};
            //ADAPTER
            adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.sports_item, from, to);
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

                Toast.makeText(getActivity(), data.get(pos).get("Sports"), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
