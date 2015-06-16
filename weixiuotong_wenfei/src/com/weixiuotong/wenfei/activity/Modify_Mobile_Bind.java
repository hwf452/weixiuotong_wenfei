package com.weixiuotong.wenfei.activity;



import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;


import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.SyncHttp;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Modify_Mobile_Bind extends Activity implements OnClickListener {
	private EditText et1,et2;
	private Button btn1,btn2,btn3;
	private TextView tv6;

	private Handler handler;
	private Long current_time;
	private SharedPreferences sharedPreferences;
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", "6",
		"7", "8", "9", };
	private static final int SIZE = 4;
	private Integer verifyCode;
	private String phoneNumber1;
	private ProgressDialog pd;
	private ProgressDialog pd1;
	private boolean net_status_no=false;
	private Timer timer;
	private Timer timer1;
	private static final int SETTEXTVIEW5=1;
	private boolean phone_Null_Rebind=false;
	private boolean verifyCode_Null=false;
	private boolean verifyCode_Sure=false;
	private boolean phoneNumber_verifyCode=true;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_mobile_bind);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		btn3=(Button)findViewById(R.id.button3);
		tv6=(TextView)findViewById(R.id.textView6);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case SETTEXTVIEW5:
					if(timer!=null){
						timer.cancel();
					}
					
					if(pd!=null){
						pd.dismiss();
					}
					if(timer1!=null){
						timer1.cancel();
					}
					if(pd1!=null){
						pd1.dismiss();
					}
					
					tv6.setText(msg.obj.toString());
					break;
				
				
				}

			}

		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.button1:
			finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);
			break;
		case R.id.button2:
			getVerify();
			
			break;

		case R.id.button3:
			reBindPhone();
			
			break;
		}
		
	}

	@SuppressWarnings("deprecation")
	private void reBindPhone() {
		if (et1.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SETTEXTVIEW5;
			msg.obj = "手机不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			phone_Null_Rebind = true;
		}
		// 验证码不能为空
		if (et2.getText().toString().equals("")) {
			Message msg = Message.obtain();
			msg.what = SETTEXTVIEW5;
			msg.obj = "验证码不能为空!";
			handler.sendMessage(msg);
			return;
		} else {
			verifyCode_Null = true;
		}
		if (verifyCode != null) {
			if (String.valueOf(verifyCode).equals(et2.getText().toString())) {
				verifyCode_Sure = true;
			} else {
				Message msg = Message.obtain();
				msg.what = SETTEXTVIEW5;
				msg.obj = "输入的验证码不正确!";
				handler.sendMessage(msg);
				return;
			}

		}
		
		if((et1.getText().toString().equals(phoneNumber1)==false)&&phoneNumber1!=null){
			tv6.setText("手机号码与验证码不匹配!");
			phoneNumber_verifyCode=false;
			return;
		}
		
		
		if(phone_Null_Rebind&&verifyCode_Null&&verifyCode_Sure&&phoneNumber_verifyCode){
			int uid=sharedPreferences.getInt("UID",0000000000);
			if(uid!=0000000000){
				String UID=String.valueOf(uid);
				Log.d("UID",UID);
				String url=ContantUtil.REBIND_PHONE_URL+"?UID="+UID+"&newPhone="+et1.getText().toString();
				
				Log.d("url", url);
				Log.d("phone_number", et1.getText().toString());
				Log.d("verifyCode", String.valueOf(verifyCode));
				
				pd1 = new ProgressDialog(this);
				pd1.setTitle("正在绑定手机...");
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
				
				new MyAsyncTask1(this).execute(url);
				
				timer1=new Timer();
				timer1.schedule(new TimerTask() {
					
					@Override
					public void run() {
						
						Message msg = Message.obtain();
						msg.what = SETTEXTVIEW5;
						msg.obj = "获取验证码失败,请检查网络";
						net_status_no=true;
						handler.sendMessage(msg);
						
					}
				}, 8000);
			}

			
			
			
		}
		
		
	}

	@SuppressWarnings("deprecation")
	private void getVerify() {
		long current_send_phone_message = System.currentTimeMillis();
		if (et1.getText().toString().equals("")) {
			tv6.setText("手机不能为空!");
			return;
		} else {
			
			
			tv6.setText("");
			if (current_time == null
					||((et1.getText().toString().equals(phoneNumber1)
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
				Log.i("verifyCode", String.valueOf(verifyCode));

				pd = new ProgressDialog(this);
				pd.setTitle("获取验证码...");
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
				phoneNumber1 = et1.getText().toString();
				int uid=sharedPreferences.getInt("UID",0000000000);
				if(uid!=0000000000){
					String UID=String.valueOf(uid);
					Log.d("UID",UID);
					String url=ContantUtil.SEND_REBINDPHONEVERIFY+"?UID="+UID+"&newPhone="+et1.getText().toString()+"&verifyCode="+sb.toString();
					
					Log.d("url", url);
					Log.d("phone_number", et1.getText().toString());
					Log.d("verifyCode", String.valueOf(verifyCode));
					new MyAsyncTask(this).execute(url);
					
					timer=new Timer();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							
							Message msg = Message.obtain();
							msg.what = SETTEXTVIEW5;
							msg.obj = "获取验证码失败,请检查网络!";
							net_status_no=true;
							handler.sendMessage(msg);
							
						}
					}, 8000);
				}
				
				
				
			} else if (et1.getText().toString().equals(phoneNumber1)
					&& current_send_phone_message < current_time) {
				Message msg = Message.obtain();
				msg.what = SETTEXTVIEW5;
				msg.obj = "一分钟内不能重复获取验证码!";
				handler.sendMessage(msg);
			}
		}
		
		
	}
	class MyAsyncTask extends AsyncTask<String, Void, String>{
		private Context context;

		public MyAsyncTask(Context context) {
			
			this.context = context;
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				SyncHttp syncHttp=new SyncHttp(context);
				String str=syncHttp.httpGet(params[0]);
				
				return str;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}

		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
				try {
					JSONObject jsonObject=new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						Message msg = Message.obtain();
						msg.what = SETTEXTVIEW5;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = SETTEXTVIEW5;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 101) {
						Message msg = Message.obtain();
						msg.what = SETTEXTVIEW5;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					}
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	
	class MyAsyncTask1 extends AsyncTask<String, Void, String>{
		private Context context;

		public MyAsyncTask1(Context context) {
			
			this.context = context;
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				SyncHttp syncHttp=new SyncHttp(context);
				String str=syncHttp.httpGet(params[0]);
				
				return str;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}

		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
				try {
					JSONObject jsonObject=new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						Message msg = Message.obtain();
						msg.what = SETTEXTVIEW5;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = SETTEXTVIEW5;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					} 
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

}
