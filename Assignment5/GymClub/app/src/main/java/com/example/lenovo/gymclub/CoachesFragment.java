package com.example.lenovo.gymclub;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import static cn.bmob.v3.Bmob.getApplicationContext;

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
        coachList.add(new Coach(92, R.drawable.coach1, "James", "James@coach.com", "13300000000"));
        coachList.add(new Coach(12, R.drawable.coach2, "Ben", "Ben@coach.com", "13300000001"));
        coachList.add(new Coach(45, R.drawable.coach3, "Ken", "Ken@coach.com", "13300000002"));
        coachList.add(new Coach(78, R.drawable.coach4, "Ron", "Ron@coach.com", "13300000003"));

        SQLiteDatabase db;
        TrainerHelper helper=new TrainerHelper(getActivity());
        db=helper.getWritableDatabase();
        for(Coach coach:coachList)
            helper.insertItem(db,coach.getId(),coach.getImage(),coach.getName(),coach.getEmail(),coach.getPhone());
        recyclerView.setAdapter(new CoachAdapter(getActivity(), coachList));

        return view;
    }


    private class CoachAdapter extends
            RecyclerView.Adapter<CoachAdapter.ViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Coach> coachList;

        public CoachAdapter(Context context, List<Coach> coachList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.coachList = coachList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView CoachImage;
            TextView CoachId, CoachName;
            ImageButton CoachEmailBtn, CoachSmsBtn, CoachCallBtn;
            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                CoachImage = (ImageView) itemView.findViewById(R.id.coach_image);
                CoachId = (TextView) itemView.findViewById(R.id.coach_id);
                CoachName = (TextView) itemView.findViewById(R.id.coach_name);
                CoachEmailBtn = (ImageButton) itemView.findViewById(R.id.coach_email);
                CoachSmsBtn = (ImageButton) itemView.findViewById(R.id.coach_sms);
                CoachCallBtn = (ImageButton) itemView.findViewById(R.id.coach_call);
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
            final BmobUser user = MainActivity.getUser();
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
            viewHolder.CoachEmailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent email = new Intent(android.content.Intent.ACTION_SEND);
                    email.setType("plain/text");
                    String[] emailReciver = { "", member.getEmail() };
                    //设置邮件默认地址
                    email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
                    //设置邮件默认标题
                    email.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    //设置要默认发送的内容
                    email.putExtra(android.content.Intent.EXTRA_TEXT, "");
                    //调用系统的邮件系统
                    startActivity(Intent.createChooser(email, "请选择邮件发送软件"));
                }
            });
            viewHolder.CoachSmsBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("IntentReset")
                @Override
                public void onClick(View v) {
                    Uri smsToUri = Uri.parse("smsto:");
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                    sendIntent.putExtra("address", member.getPhone());
                    startActivity(sendIntent);
                }
            });
            viewHolder.CoachCallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.DIAL");
                    intent.setData(Uri.parse("tel:" + member.getPhone()));
                    startActivity(intent);
                }
            });
        }
    }

    private class Coach {
        private int id;
        private String name;
        private int image;
        private String email;
        private String phone;

        public Coach(int id, int image, String name, String email, String phone) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.email = email;
            this.phone = phone;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
