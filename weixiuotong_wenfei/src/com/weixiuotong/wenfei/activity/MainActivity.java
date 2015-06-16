package com.weixiuotong.wenfei.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{

	private static MainActivity mainActivity;
	RadioButton rb1, rb2, rb3, rb4;
	TabHost tabHost;
	

	private RadioGroup radioGroup;

	public MainActivity() {
		mainActivity = this;
	}

	public static MainActivity getInstance() {
		return mainActivity;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		rb1 = (RadioButton) findViewById(R.id.radio0);
		rb2 = (RadioButton) findViewById(R.id.radio1);
		rb3 = (RadioButton) findViewById(R.id.radio2);
		rb4 = (RadioButton) findViewById(R.id.radio3);
		
		
		initTabView();
	}

	private void initTabView() {

		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("提交维修单").setContent(R.id.fragment1)
				.setIndicator("提交维修单"));
		tabHost.addTab(tabHost.newTabSpec("提醒").setContent(R.id.fragment2)
				.setIndicator("提醒"));
		tabHost.addTab(tabHost.newTabSpec("我的维修单").setContent(R.id.fragment3)
				.setIndicator("我的维修单"));
		tabHost.addTab(tabHost.newTabSpec("更多").setContent(R.id.fragment4)
				.setIndicator("更多"));

		radioGroup.setOnCheckedChangeListener(this);
		
			

	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		switch (checkedId) {
		case R.id.radio0:
			tabHost.setCurrentTab(0);
			

			break;
		case R.id.radio1:
			tabHost.setCurrentTab(1);
			

			break;
		case R.id.radio2:
			tabHost.setCurrentTab(2);
			
			break;
		case R.id.radio3:
			tabHost.setCurrentTab(3);
			
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:

			new AlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("您确定要退出维修通?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									finish();

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

			break;

		}
		return true;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {

		}

		// 切换为横屏

		else if (newConfig.orientation == this.getResources()
				.getConfiguration().ORIENTATION_LANDSCAPE) {

		}

	}

	

}
