package com.example.lenovo.gymclub;

/**
 * Created by 16301 on 2018/11/13.
 */
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class LogInFragment extends Fragment implements  PlatformActionListener {
    private EditText mEtUserName = null;
    private EditText mEtPassWord = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_in, container, false);

        mEtUserName = (EditText) view.findViewById(R.id.LogInUserName);
        mEtPassWord = (EditText) view.findViewById(R.id.LogInUserPassword);

        Button mBtn = (Button) view.findViewById(R.id.login);
        mBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = mEtUserName.getText().toString();
                String password = mEtPassWord.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobUser user = MainActivity.getUser();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<BmobUser>(){
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e == null) {
                            Toast.makeText(getActivity(),"Log in Successfully.",Toast.LENGTH_SHORT).show();
                            MainActivity activity = (MainActivity) getActivity();
                            if (activity != null) {
                                TextView textView = activity.findViewById(R.id.info_username);
                                textView.setText(bmobUser.getUsername());
                                NavigationView navigationView = activity.findViewById(R.id.nav_view);
                                Menu communicateMenu = navigationView.getMenu().getItem(5).getSubMenu();
                                communicateMenu.getItem(1).setVisible(false);
                                communicateMenu.getItem(2).setVisible(false);
                                communicateMenu.getItem(3).setVisible(true);
                                while (MainActivity.getFragment() > 0)
                                    activity.onBackPressed();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Log in Failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Button mBtnQQ = (Button) view.findViewById(R.id.loginWithQQ);
        mBtnQQ.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginByQQ();
            }
        });
        return view;
    }



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getActivity(), "授权登陆成功", Toast.LENGTH_SHORT).show();
                    //获取用户资料
                    Platform platform = (Platform) msg.obj;
                    String userId = platform.getDb().getUserId();//获取用户账号
                    String userName = platform.getDb().getUserName();//获取用户名字
                    String userIcon = platform.getDb().getUserIcon();//获取用户头像
                    String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        TextView textView = activity.findViewById(R.id.info_username);
                        textView.setText(userName);
                    }
                    break;
                case 2:
                    Toast.makeText(getActivity(), "授权登陆失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "授权登陆取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void loginByQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this);
        qq.SSOSetting(false);
        if (!qq.isClientValid()) {
            Toast.makeText(getActivity(), "QQ未安装,请先安装QQ",
                    Toast.LENGTH_SHORT).show();
        }
        authorize(qq);
    }

    private void authorize(Platform platform) {
        if (platform == null) {
            return;
        }
        if (platform.isAuthValid())
        { //如果授权就删除授权资料
            platform.removeAccount(true);
        }
        platform.showUser(null);//授权并获取用户信息
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

}
