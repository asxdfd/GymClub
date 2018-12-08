package com.example.lenovo.gymclub;

/**
 * Created by 16301 on 2018/11/13.
 */
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LogInFragment extends Fragment implements View.OnClickListener{
    private EditText mEtUserName = null;
    private EditText mEtPassWord = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_in, container, false);
        mEtUserName = (EditText) view.findViewById(R.id.LogInUserName);
        mEtPassWord = (EditText) view.findViewById(R.id.LogInUserPassword);
        Button mBtn = (Button) view.findViewById(R.id.login);
        mBtn.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        final String username = mEtUserName.getText().toString();
        String password = mEtPassWord.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "用户名密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>(){
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(getActivity(),"Log in Successful.",Toast.LENGTH_SHORT).show();
                    TextView textview = getActivity().findViewById(R.id.header_userName);
                    textview.setText(username);
                }else{
                    Toast.makeText(getActivity(),"Log in Failure.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
