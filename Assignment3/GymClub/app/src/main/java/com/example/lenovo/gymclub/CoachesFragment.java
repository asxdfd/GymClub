package com.example.lenovo.gymclub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CoachesFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.coaches, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Coach> coachList = new ArrayList<>();
        coachList.add(new Coach(92, R.drawable.coach1, "James"));
        coachList.add(new Coach(12, R.drawable.coach2, "Ben"));
        coachList.add(new Coach(45, R.drawable.coach3, "Ken"));
        coachList.add(new Coach(78, R.drawable.coach4, "Ron"));
        recyclerView.setAdapter(new CoachAdapter(getActivity(), coachList));

        return view;
    }


    private class CoachAdapter extends
            RecyclerView.Adapter<CoachAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private  List<Coach>  coachList;

        public CoachAdapter(Context context,  List<Coach> coachList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.coachList = coachList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView CoachImage;
            TextView CoachId, CoachName;
            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                CoachImage = (ImageView) itemView.findViewById(R.id.coach_image);
                CoachId = (TextView) itemView.findViewById(R.id.coach_id);
                CoachName = (TextView) itemView.findViewById(R.id.coach_name);
            }
        }

        @Override
        public int getItemCount() {
            return coachList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = layoutInflater.inflate(
                    R.layout.coach_item, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final Coach member = coachList.get(position);
            viewHolder.CoachImage.setImageResource(member.getImage());
            viewHolder.CoachId.setText(String.valueOf(member.getId()));
            viewHolder.CoachName.setText(member.getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(member.getImage());
                    Toast toast = new Toast(context);
                    toast.setView(imageView);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    public class Coach {
        private int id;
        private String name;
        private int image;

        public Coach(int id, int image, String name) {
            this.id=id;
            this.image=image;
            this.name=name;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }

        public int getImage() {
            return image;
        }
        public void setImage(int image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
