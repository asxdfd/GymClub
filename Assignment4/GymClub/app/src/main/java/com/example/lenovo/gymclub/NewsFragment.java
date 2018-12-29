package com.example.lenovo.gymclub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class NewsFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<News> newsList = new ArrayList<>();
        newsList.add(new News(R.drawable.news1, "svvsdbsdbsdbsdnb"));
        newsList.add(new News(R.drawable.news2, "fndfndfn"));
        newsList.add(new News(R.drawable.news3, "hdndn"));
        newsList.add(new News(R.drawable.news4, "fdfndfndn"));

        SQLiteDatabase db;
        DbHelper dbHelper = new DbHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 1);
        for (News news : newsList)
            dbHelper.insertItem(db, news.getName(), news.getImage());
        recyclerView.setAdapter(new NewsAdapter(getActivity(), newsList));

        return view;
    }


    private class NewsAdapter extends
            RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<News> newsList;

        public NewsAdapter(Context context, List<News> newsList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.newsList = newsList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView newsImage;
            TextView newsName;
            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                newsImage = (ImageView) itemView.findViewById(R.id.news_image);
                newsName = (TextView) itemView.findViewById(R.id.news_name);
            }
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = layoutInflater.inflate(
                    R.layout.news_item, viewGroup, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            final News member = newsList.get(position);
            viewHolder.newsImage.setImageResource(member.getImage());
            viewHolder.newsName.setText(member.getName());
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

    private class News {
        private String name;
        private int image;


        public News(int image, String name) {
            this.image = image;
            this.name = name;
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
