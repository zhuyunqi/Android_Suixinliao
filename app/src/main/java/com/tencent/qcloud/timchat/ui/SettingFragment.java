package com.tencent.qcloud.timchat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.ProfilePresenter;
import com.tencent.qcloud.presentation.presenter.SettingsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ProfileView;
import com.tencent.qcloud.presentation.viewfeatures.SettingsFeature;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.UserInfo;

/**
 * 设置页面
 */
public class SettingFragment extends Fragment implements SettingsFeature,ProfileView{

    private static final String TAG = SettingFragment.class.getSimpleName();
    private SettingsPresenter mSettingsPresenter;
    private ProfilePresenter profilePresenter;
    private TextView id,name;



    public SettingFragment() {
        // Required empty public constructor
    }

    private TextView mStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        id = (TextView) view.findViewById(R.id.idtext);
        name = (TextView) view.findViewById(R.id.name);
        mSettingsPresenter = new SettingsPresenter(this,getActivity());
        mSettingsPresenter.setDefaultAllowType();
        profilePresenter = new ProfilePresenter(this);
        profilePresenter.getProfile();
        TextView logout = (TextView) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsPresenter.Logout();

            }
        });
        return view ;
    }


    @Override
    public void showMyAllowedStatus(int status) {
        if(status==1){

        }
    }

    /**
     * 退出回调
     *
     * @param isSuccess 是否成功
     */
    @Override
    public void onLogoutResult(boolean isSuccess) {
        if (isSuccess){
            UserInfo.getInstance().setId(null);
            Intent intent = new Intent(getActivity(),SplashActivity.class);
            getActivity().finish();
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.setting_logout_fail),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示用户信息
     *
     * @param profile
     */
    @Override
    public void showProfile(TIMUserProfile profile) {
        name.setText(profile.getNickName());
        id.setText(profile.getIdentifier());
    }
}
