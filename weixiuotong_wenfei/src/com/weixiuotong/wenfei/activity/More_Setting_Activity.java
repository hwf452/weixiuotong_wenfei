package com.weixiuotong.wenfei.activity;



import com.weixiuotong.wenfei.service.UpdateManager;
import com.weixiuotong.wenfei.user_activity.LoginActivity;
import com.weixiuotong.wenfei.util.DataCleanManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class More_Setting_Activity extends Activity implements OnClickListener {
	private Button btn1, btn2, btn3, btn4, btn5, btn6,btn7;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_setting_activity);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button3);
		btn3 = (Button) findViewById(R.id.button5);
		btn4 = (Button) findViewById(R.id.button6);
		btn5 = (Button) findViewById(R.id.button8);
		btn6 = (Button) findViewById(R.id.button10);
		btn7=(Button)findViewById(R.id.button12);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent=new Intent(this,Private_Imformation_Activity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);  
			break;

		case R.id.button3:
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			
			
			
			
			
			break;
		case R.id.button5:
			DataCleanManager.cleanInternalCache(getApplicationContext());
			DataCleanManager.cleanFiles(getApplicationContext());
			Toast.makeText(this,"清理缓存成功",Toast.LENGTH_SHORT).show();
			break;
		case R.id.button6:
			Intent intent2=new Intent(this,About_Me_Activity.class);
			startActivity(intent2);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);  
			
			break;
		case R.id.button8:
			UpdateManager manager = new UpdateManager(this);
			// 检查软件更新
			manager.checkUpdate();
			
			break;
		case R.id.button10:
			sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
			sharedPreferences.edit().remove("UID").commit();
			
			Intent intent4=new Intent(this,LoginActivity.class);
			startActivity(intent4);
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			More_Setting_Activity.this.finish();
			
			break;
		case R.id.button12:
			Intent intent12=new Intent(this,Modify_PassWrod.class);
			startActivity(intent12);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			
			
			break;
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
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
									More_Setting_Activity.this.finish();
									

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


}