package com.example.lenovo.gymclub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private EditText mEtUserName = null;
    private EditText mEtPassWord = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);

        mEtUserName = (EditText) view.findViewById(R.id.signUpUserName);
        mEtPassWord = (EditText) view.findViewById(R.id.signUpUserPassword);

        Button mBtn = (Button) view.findViewById(R.id.register);
        mBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String username = mEtUserName.getText().toString();
        String password = mEtPassWord.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "用户名密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        /**
         * Bmob注册
         */
        final BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(getActivity(),"Sign Up Successful.",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(),"Sign Up Failure.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
