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

public class ScheduleFragment extends ListFragment {



    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    private boolean added = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        if (!added) {
            //MAP
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Icon", Integer.toString(R.drawable.ic_menu_add));
            map.put("Date", "Add a new plan");
            data.add(map);
            String[] from = {"Icon", "Date"};
            int[] to = {R.id.taskicon, R.id.datetext};
            //ADAPTER
            adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.schedule_item, from, to);
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

                Toast.makeText(getActivity(), "Add new plans", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
