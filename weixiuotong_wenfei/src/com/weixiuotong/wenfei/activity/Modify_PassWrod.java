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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Modify_PassWrod extends Activity implements OnClickListener {
	private Button btn1,btn2;
	private TextView tv5;
	private EditText et1,et2,et3;
	private Handler handler;
	private ProgressDialog pd;
	private Timer timer;
	private SharedPreferences sharedPreferences;
	private static final int MODIFY_PWD_STATUS=1;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_passwrod);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		tv5=(TextView)findViewById(R.id.textView5);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		et3=(EditText)findViewById(R.id.editText3);
		sharedPreferences = this.getSharedPreferences("userinfo", MODE_PRIVATE);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {

				case MODIFY_PWD_STATUS:
					if(pd!=null){
						pd.dismiss();
					}
					if(timer!=null){
						timer.cancel();
					}

					tv5.setText(msg.obj.toString());
					
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
			modify_pwd();
			
			break;
		}
		
	}

	@SuppressWarnings("deprecation")
	private void modify_pwd() {
		if(et1.getText().toString().equals("")){
			tv5.setText("请输入旧密码!");
			return;
		}
		if(et1.getText().toString().length()<6){
			tv5.setText("您输入的旧密码长度不符!");
			return;
		}
		
		if(et2.getText().toString().equals("")){
			tv5.setText("新密码不能为空!");
			return;
		}
		if(et2.getText().toString().length()<6){
			tv5.setText("您输入的新密码长度不符!");
			return;
		}
		
		if(et3.getText().toString().equals("")){
			tv5.setText("确认密码不能为空!");
			return;
		}
		if(et2.getText().toString().length()<6){
			tv5.setText("您输入的确认密码长度不符!");
			return;
		}
		if((et2.getText().toString().equals(et3.getText().toString()))==false){
			tv5.setText("两次输入的新密码不一致!");
			return;
		}
		List<Parameter> params = new ArrayList<Parameter>();
		Parameter parameter = new Parameter("oldPwd", et1.getText()
				.toString());
		Parameter parameter1 = new Parameter("newPwd", et3.getText()
				.toString());
		params.add(parameter);
		params.add(parameter1);
		int uid = sharedPreferences.getInt("UID", 0000000000);
		if (uid != 0000000000) {
			String UID = String.valueOf(uid);
			Parameter parameter2 = new Parameter("UID", UID);
			params.add(parameter2);
		}
		String url = ContantUtil.MODIFY_PASSWROD_URL;

		pd = new ProgressDialog(this);
		pd.setTitle("修改密码中...");
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
				msg.what = MODIFY_PWD_STATUS;
				msg.obj = "修改密码失败,请检查网络";

				handler.sendMessage(msg);

			}
		}, 5000);
		
		
		
		
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
						msg.what = MODIFY_PWD_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);
						sharedPreferences.edit().remove("UID").commit();

					} else if (opCode == 100) {
						Message msg = Message.obtain();
						msg.what = MODIFY_PWD_STATUS;
						msg.obj = jsonObject.getString("opMess");
						handler.sendMessage(msg);

					}
					else if (opCode == 101) {
						Message msg = Message.obtain();
						msg.what = MODIFY_PWD_STATUS;
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
