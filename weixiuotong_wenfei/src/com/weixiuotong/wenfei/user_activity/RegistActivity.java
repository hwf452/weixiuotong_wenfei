package com.weixiuotong.wenfei.user_activity;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.example.httpdemo.http.HttpUtils;
import com.example.httpdemo.http.HttpUtils.OnHttpToolsListener;

import com.weixiuotong.wenfei.activity.R;
import com.weixiuotong.wenfei.contant.ContantUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

//注册页面
public class RegistActivity extends Activity implements OnClickListener,
		OnHttpToolsListener, OnCheckedChangeListener {
	// 声明控件
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private EditText et1, et2, et3, et4, et5, et6;
	private TextView tv8;
	private HttpUtils httpUtils;
	private boolean pwd_sure = false;
	private boolean verify_sure = false;
	private boolean verify_null = false;
	private boolean userName_null = false;
	private boolean pwd_null = false;
	private boolean pwd_sure_null = false;
	private boolean phone_null = false;
	private boolean nickname_null = false;
	private boolean check_item = true;
	private boolean phoneNumber_verifycode_sure=true;
	private CheckBox checkBox1; 
	private Timer timer;
	private Timer timer1;
	


	String pwd;

	String pwd1;
	private Handler handler;
	private Long current_time;

	private static final int REGIST_INFO = 1;
	private static final int SEND_VERIFYCODE_STATUS = 2;
	private ProgressDialog pd;
	private ProgressDialog pd1;
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", };
	private static final int SIZE = 4;
	private Integer verifyCode;
	private String phoneNumber1;
	private boolean net_status_no=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		httpUtils = new HttpUtils(this);
		httpUtils.registerToolsListener(this);

		// 查找控件
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		et4 = (EditText) findViewById(R.id.editText4);
		et5 = (EditText) findViewById(R.id.editText5);
		et6 = (EditText) findViewById(R.id.editText6);
		tv8 = (TextView) findViewById(R.id.textView8);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkBox1.setOnCheckedChangeListener(this);

		pwd = et2.getText().toString();
		pwd1 = et3.getText().toString();
		// 绑定事件
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);

		String text1 = "使用条款";
		TextView tv = (TextView) findViewById(R.id.textView9);
		SpannableString spannableString1 = new SpannableString(text1);
		spannableString1.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Intent intent = new Intent(RegistActivity.this,
						AgreeItemActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

			}
		}, 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(spannableString1);
		tv.setMovementMethod(LinkMovementMethod.getInstance());

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case REGIST_INFO:
					if(timer!=null){
						timer.cancel();
					}
					if(timer1!=null){
						timer1.cancel();
					}
					if(pd!=null){
						pd.dismiss();
					}
					if(pd1!=null){
						pd1.cancel();
					}
					
					tv8.setText(msg.obj.toString());
					break;
				case SEND_VERIFYCODE_STATUS:
					tv8.setText(msg.obj.toString());
					break;
				
				}

			}

		};
	}

	@Override
	public void onClick(View v) {
		// 判断点击哪个按钮
		switch (v.getId()) {
		case R.id.button1:

			finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;

		case R.id.button2:
			register();

			break;
		case R.id.button3:
			send_phone_message();
			break;
		}

	}

	private void send_phone_message() {
		long current_send_phone_message = System.currentTimeMillis();
		// 手机号为空
		if (et4.getText().toString().equals("")) {
			tv8.setText("手机不能为空!");
			return;
		} else {
			if (current_time == null
					||((et4.getText().toString().equals(phoneNumber1)
							&& current_send_phone_message > current_time))||net_status_no) {
				current_time = System.currentTimeMillis() + 60000;

				Random random = new Random();
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i <= SIZE; i++) {
					int r = random.nextInt(chars.length);

					sb.append(chars[r]);

				}

				if (sb != null) {
					verifyCode = Integer.valueOf(sb.toString());

				}
				Log.d("verifycode",sb.toString());
				

				pd1 = new ProgressDialog(this);
				pd1.setTitle("获取验证码...");
				pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd1.setButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						pd1.dismiss();
						if(timer1!=null){
							timer1.cancel();
						}

					}
				});
				pd1.show();
				phoneNumber1 = et4.getText().toString();
				String url = ContantUtil.SEND_REGPHONEVERIFY + "?phone="
						+ et4.getText().toString() + "&verifyCode="
						+ sb.toString();
				
				httpUtils.sendGetHttpRequest(url);
				timer1=new Timer();
				timer1.schedule(new TimerTask() {
					
					@Override
					public void run() {
						
						Message msg = Message.obtain();
						msg.what = REGIST_INFO;
						msg.obj = "获取验证码失败,请检查网络";
						net_status_no=true;
						handler.sendMessage(msg);
						
					}
				}, 8000);
				
				
			} else if (et4.getText().toString().equals(phoneNumber1)
					&& current_send_phone_message < current_time) {
				Message msg = Message.obtain();
				msg.what = SEND_VERIFYCODE_STATUS;
				msg.obj = "一分钟内不能重复获取验证码!";
				handler.sendMessage(msg);
			}

		}

	}

	private void register() {
		// 账号是否为空
		if (et1.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "账号不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			userName_null = true;
		}
		if(et1.getText().toString().length()<6){
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "账号不能小于6个字符!";
			handler.sendMessage(msg);
			return;
		}
		// 密码不能为空
		if (et2.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "密码不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			pwd_null = true;
		}
		if(et2.getText().toString().length()<6){
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "密码不能小于6个字符!";
			handler.sendMessage(msg);
			return;
		}
		// 密码确认不能为空
		if (et3.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "密码确认不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			pwd_sure_null = true;
		}

		// 验证前后两次输入密码是否一致
		if (et2.getText().toString().equals(et3.getText().toString())) {
			pwd_sure = true;
		} else {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "前后两次输入的密码不一致!";
			handler.sendMessage(msg);
			return;
		}
		// 手机号为空
		if (et4.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "手机不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			phone_null = true;
		}
		// 昵称是否为空
		if (et5.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "昵称不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			nickname_null = true;
		}
		// 验证码是否为空
		if (et6.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "验证码不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			verify_null = true;
		}

		// 检查输入的验证码是否正确
		if (verifyCode != null) {
			if (String.valueOf(verifyCode).equals(et6.getText().toString())) {
				verify_sure = true;
			} else {
				Message msg = Message.obtain();
				msg.what = SEND_VERIFYCODE_STATUS;
				msg.obj = "输入的验证码不正确!";
				handler.sendMessage(msg);
				return;
			}

		}
		if(et4.getText().toString().equals(phoneNumber1)==false&&phoneNumber1!=null){
			Message msg = Message.obtain();
			msg.what = SEND_VERIFYCODE_STATUS;
			msg.obj = "手机号码与验证码不匹配!";
			handler.sendMessage(msg);
			phoneNumber_verifycode_sure=false;
			return;
			
		}

		if (userName_null && pwd_null && pwd_sure_null && pwd_sure
				&& phone_null && nickname_null && verify_null && verify_sure
				&& check_item&&phoneNumber_verifycode_sure) {
			pd = new ProgressDialog(this);
			pd.setTitle("注册提交...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					pd.dismiss();
					if(timer!=null){
						timer.cancel();
					}

				}
			});
			pd.show();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", et1.getText().toString());
			map.put("passWord", et2.getText().toString());
			map.put("nickName", et5.getText().toString());
			map.put("phone", et4.getText().toString());
			map.put("regIP", "android");

			httpUtils.sendPostHttpRequest(map, ContantUtil.REGIST_URL);
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = "注册失败,请检查网络";
					handler.sendMessage(msg);
					
				}
			}, 5000);
		}

	}

	@Override
	public void getData(boolean isSuccess, String content) {
		if (isSuccess) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				int opCode = jsonObject.getInt("opCode");
				if (opCode == 99) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				} else if (opCode == 100) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				} else if (opCode == 101) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				} else if (opCode == 102) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				} else if (opCode == 103) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				} else if (opCode == 104) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				} else if (opCode == 105) {
					Message msg = Message.obtain();
					msg.what = REGIST_INFO;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if(timer!=null){
				timer.cancel();
			}
			
			if(timer1!=null){
				timer1.cancel();
			}
			finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;

		}
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBox1:
			if (checkBox1.isChecked()) {
				check_item = true;
				
				tv8.setText("");

			} else {
				check_item = false;
				
				tv8.setText("请阅读并同意使用条款!");

			}

			break;

		}

	}

}
