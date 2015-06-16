package com.weixiuotong.wenfei.activity;

import java.util.ArrayList;

import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;


import com.weixiuotong.wenfei.contant.ContantUtil;
import com.weixiuotong.wenfei.service.Parameter;
import com.weixiuotong.wenfei.service.SyncHttp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Modify_Address_Activity extends Activity implements OnClickListener{
	private EditText et1;
	private Button btn1,btn2;
	private TextView tv3;

	private Handler handler;
	private static final int UPDATE_STATUS = 1;
	private ProgressDialog pd;
	private Timer timer;
	private SharedPreferences sharedPreferences;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_address_activity);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		et1 = (EditText) findViewById(R.id.editText1);
		btn1 = (Button) findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		tv3 = (TextView) findViewById(R.id.textView3);
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

					tv3.setText(msg.obj.toString());
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
			Modify_Address();
			
			break;

		}
		
	}

	@SuppressWarnings("deprecation")
	private void Modify_Address() {
		if(et1.getText().toString().equals("")){
			tv3.setText("地址不能为空!");
		}else{
			Intent intent=getIntent();
			Bundle bundle=intent.getExtras();
			
			String NickName=bundle.getString("NickName");
		List<Parameter> params = new ArrayList<Parameter>();
		Parameter parameter = new Parameter("address", et1.getText()
				.toString());
		params.add(parameter);
		
		
		Parameter parameter3=new Parameter("nickName",NickName);
		params.add(parameter3);
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {
			String UID = String.valueOf(uid);
			Parameter parameter4 = new Parameter("UID", UID);
			params.add(parameter4);
		}
		String url = ContantUtil.MODIFY_PRIVATE_IMFORMATION;

		pd = new ProgressDialog(this);
		pd.setTitle("更新中...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pd.dismiss();
				if (timer != null) {
					timer.cancel();
				}

			}
		});
		pd.show();

		new MyAsyncTask(this, params).execute(url);

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Message msg = Message.obtain();
				msg.what = UPDATE_STATUS;
				msg.obj = "获取验证码失败,请检查网络";

				handler.sendMessage(msg);

			}
		}, 8000);
		}
		
	}
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		private Context context;
		List<Parameter> params1;

		public MyAsyncTask(Context context, List<Parameter> params) {
			this.params1 = params;
			this.context = context;
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				SyncHttp syncHttp = new SyncHttp(context);
				String str = syncHttp.httpPost(params[0], params1);

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

					} else if (opCode == 100) {
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if(timer!=null){
				timer.cancel();
			}
			
			
			finish();
			overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

			break;

		}
		return true;
	}


}
