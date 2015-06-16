package com.weixiuotong.wenfei.activity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import com.weixiuotong.wenfei.service.UpdateManager;
import com.weixiuotong.wenfei.user_activity.LoginActivity;
import com.weixiuotong.wenfei.util.DataCleanManager;


import android.content.Intent;
import android.content.SharedPreferences;



import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Home_Tab4_Fragment extends FragmentToOtherActivity implements OnClickListener {
	private Button btn1, btn2, btn3, btn4, btn5, btn6,btn7;
	private SharedPreferences sharedPreferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.more_setting_activity, null);
		sharedPreferences = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);
		btn1 = (Button) view.findViewById(R.id.button1);
		btn2 = (Button) view.findViewById(R.id.button3);
		btn3 = (Button) view.findViewById(R.id.button5);
		btn4 = (Button) view.findViewById(R.id.button6);
		btn5 = (Button) view.findViewById(R.id.button8);
		btn6 = (Button) view.findViewById(R.id.button10);
		btn7=(Button)view.findViewById(R.id.button12);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent=new Intent(getActivity(),Private_Imformation_Activity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);  
			break;

		case R.id.button3:
			MainActivity.getInstance().tabHost.setCurrentTab(2);
			MainActivity.getInstance().rb3.setChecked(true);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			
			
			
			
			
			break;
		case R.id.button5:
			DataCleanManager.cleanInternalCache(getActivity());
			DataCleanManager.cleanFiles(getActivity());
			Toast.makeText(getActivity(),"清理缓存成功",Toast.LENGTH_SHORT).show();
			break;
		case R.id.button6:
			Intent intent2=new Intent(getActivity(),About_Me_Activity.class);
			startActivity(intent2);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);  
			
			break;
		case R.id.button8:
			UpdateManager manager = new UpdateManager(getActivity());
			// 检查软件更新
			manager.checkUpdate();
			
			break;
		case R.id.button10:
			sharedPreferences = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);
			sharedPreferences.edit().remove("UID").commit();
			
			Intent intent4=new Intent(getActivity(),LoginActivity.class);
			startActivity(intent4);
			getActivity().overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			getActivity().finish();
			
			break;
		case R.id.button12:
			Intent intent12=new Intent(getActivity(),Modify_PassWrod.class);
			startActivity(intent12);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			
			
			break;
		}
		
	}

}
