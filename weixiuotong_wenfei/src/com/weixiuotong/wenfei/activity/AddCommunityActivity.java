package com.weixiuotong.wenfei.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;


import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.SyncHttp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddCommunityActivity extends Activity implements OnClickListener{
	private Button btn1,btn2;
	private TextView tv6;
	private EditText et1,et2;
	private SharedPreferences sharedPreferences;
	private ProgressDialog pd;
	private Timer timer;
	private Handler handler;
	private static final int UPDATE_STATUS = 1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_community_activity);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		tv6=(TextView)findViewById(R.id.textView6);
				
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case UPDATE_STATUS:
					if (timer != null) {
						timer.cancel();
					}

					if (pd != null) {
						pd.dismiss();
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
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			
			break;

		case R.id.button2:
			
			AddCommunity();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			
			break;
		
		}
		
	}
	
	private void AddCommunity() {
		if(et1.getText().toString().equals("")){
			tv6.setText("社区不能为空!");
			return;
		}
		if(et2.getText().toString().equals("")){
			tv6.setText("社区地址不能为空!");
			return;
		}
		
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if(uid!=0000000000){
			pd = new ProgressDialog(this);
			pd.setTitle("正在添加社区...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

			pd.show();
			String url = ContantUtil.ADD_COMMUNITY_URL + "?UID="
					+ String.valueOf(uid)+"&commName="+et1.getText().toString()+"&commAddr="+et2.getText().toString();
			new MyAsyncTask(this).execute(url);
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = UPDATE_STATUS;
					msg.obj = "联网失败!";

					handler.sendMessage(msg);

				}
			}, 5000);
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);

			break;

		}
		return true;
	}
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		private Context context;
		

		public MyAsyncTask(Context context) {
			
			this.context = context;
		}
		
		
		@Override
		protected String doInBackground(String... params) {

			try {
				Log.i("url",params[0]);
				SyncHttp syncHttp = new SyncHttp(context);
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
			if (result != null) {
				try {
					
					JSONObject jsonObject = new JSONObject(result);
					int opCode = jsonObject.getInt("opCode");

					if (opCode == 99) {
						Message msg = Message.obtain();
						msg.what = UPDATE_STATUS;
						msg.obj = jsonObject.getString("opMess");

						handler.sendMessage(msg);
						
					
					}else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = UPDATE_STATUS;
						msg.obj = jsonObject.getString("opMess");

						handler.sendMessage(msg);

					}else if (opCode == 101) {
						Message msg = Message.obtain();
						msg.what = UPDATE_STATUS;
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
	


}
