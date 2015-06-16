package com.weixiuotong.wenfei.user_activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.httpdemo.http.HttpUtils;
import com.example.httpdemo.http.HttpUtils.OnHttpToolsListener;

import com.weixiuotong.wenfei.activity.MainActivity;

import com.weixiuotong.wenfei.activity.R;

import com.weixiuotong.wenfei.contant.ContantUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.EditText;
import android.widget.TextView;

//用户登陆界面
public class LoginActivity extends Activity implements OnClickListener,
		OnHttpToolsListener {

	private EditText userName, pwd;
	private SharedPreferences sharedPreferences;
	private TextView tv4, tv5;
	private Button login;
	private Button regist;
	private HttpUtils httpUtils;
	private Timer timer;

	private Handler handler;
	protected static final int SET_TV8 = 1;
	protected static final int TO_EXAMPLEACTIVITY = 2;
	private ProgressDialog pd;
	private boolean userName_null = false;
	private boolean pwd_null = false;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		if ((sharedPreferences.getInt("UID", 00000000) != 00000000)) {
			Log.d("uid",
					String.valueOf(sharedPreferences.getInt("UID", 00000000)));
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			LoginActivity.this.finish();

		}

		setContentView(R.layout.login);
		httpUtils = new HttpUtils(this);
		httpUtils.registerToolsListener(this);

		userName = (EditText) findViewById(R.id.editText1);
		pwd = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.button1);
		regist = (Button) findViewById(R.id.button2);

		String text1 = "忘记密码？";
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5 = (TextView) findViewById(R.id.textView5);

		SpannableString spannableString1 = new SpannableString(text1);
		spannableString1.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Intent intent = new Intent(LoginActivity.this,
						LosspwdActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);

			}
		}, 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv5.setText(spannableString1);
		tv5.setMovementMethod(LinkMovementMethod.getInstance());
		login.setOnClickListener(this);
		regist.setOnClickListener(this);

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {

				case SET_TV8:
					pd.dismiss();
					if(timer!=null){
						timer.cancel();
					}
					
					tv4.setText(msg.obj.toString());
					break;

				case TO_EXAMPLEACTIVITY:
					pd.dismiss();
					if(timer!=null){
						timer.cancel();
					}
					
					Editor editor = sharedPreferences.edit();
					editor.putString("username", userName.getText().toString());

					editor.putString("password", pwd.getText().toString());
					int uid = Integer.parseInt(msg.obj.toString());
					Log.d("TAG", msg.obj.toString());
					editor.putInt("UID", uid);
					Log.d("uid", String.valueOf(uid));
					editor.commit();
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					LoginActivity.this.finish();
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
			userLogin();

			break;

		case R.id.button2:
			Intent intent1 = new Intent(this, RegistActivity.class);
			startActivity(intent1);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

			break;
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
			new AlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("您确定要退出维修通?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									LoginActivity.this.finish();

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

	public void userLogin() {
		if (userName.getText().toString().equals("")) {
			tv4.setText("账号不能为空!");
			return;
		} else {
			userName_null = true;
		}

		if (pwd.getText().toString().equals("")) {
			tv4.setText("密码不能为空!");
			return;
		} else {
			pwd_null = true;
		}

		if (userName_null && pwd_null) {
			Log.i("tag", userName.getText().toString());
			Log.i("tag", pwd.getText().toString());

			String url = ContantUtil.LOGIN_URL + "?userNameOrPhone="
					+ userName.getText().toString() + "&passWord="
					+ pwd.getText().toString();
			pd = new ProgressDialog(this);
			pd.setTitle("登陆中...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					pd.dismiss();
					timer.cancel();

				}
			});
			pd.show();
			

			try {
				httpUtils.sendGetHttpRequest(url);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("connect_error", "获取数据失败");
			}
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = SET_TV8;
					msg.obj = "登陆失败,请检查网络";
					handler.sendMessage(msg);
					
					
				}
			}, 8000);
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
					msg.what = TO_EXAMPLEACTIVITY;
					msg.obj = jsonObject.getInt("UID");
					handler.sendMessage(msg);

				} else if (opCode == 100) {
					Message msg = Message.obtain();
					msg.what = SET_TV8;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);

				} else if (opCode == 101) {
					Message msg = Message.obtain();
					msg.what = SET_TV8;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);

				} else if (opCode == 102) {
					Message msg = Message.obtain();
					msg.what = SET_TV8;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);

				} else if (opCode == 103) {
					Message msg = Message.obtain();
					msg.what = SET_TV8;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}
}
