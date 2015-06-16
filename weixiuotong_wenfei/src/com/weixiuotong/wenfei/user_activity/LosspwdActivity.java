package com.weixiuotong.wenfei.user_activity;

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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


//找回密码界面

public class LosspwdActivity extends Activity implements OnClickListener,OnHttpToolsListener {
	private Button btn1;
	private Button btn2;
	private EditText et1,et2;
	private ProgressDialog pd;
	private Handler handler;
	private TextView tv6;
	private static final int SET_TV3=1;
	private HttpUtils httpUtils; 
	private boolean userName_null=false;
	private boolean phone_null=false;
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loss_pwd);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		tv6=(TextView)findViewById(R.id.textView6);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		httpUtils=new HttpUtils(this);
		httpUtils.registerToolsListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		
		
		handler=new Handler(){
			public void handleMessage(android.os.Message msg){
				switch (msg.what) {
				
				case SET_TV3:
					
					if(pd!=null){
						pd.dismiss();
					}
					if(timer!=null){
						timer.cancel();
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
			send_pwd();
			
			break;
		}
		
	}

	private void send_pwd() {
		//账号输入不能为空
		if(et1.getText().toString().equals("")){
			tv6.setText("账号不能为空");
			return;
		}else{
			userName_null=true;
		}
		//手机号码不能为空
		if(et2.getText().toString().equals("")){
			tv6.setText("手机号码输入不能为空");
			return;
		}else{
			phone_null=true;
		}
		
		
		if(userName_null&&phone_null){
			pd=new ProgressDialog(this);
			pd.setTitle("发送中...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setButton("取消",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					pd.dismiss();
					if(timer!=null){
						timer.cancel();
					}
					
				}
			});
			pd.show();
			String url=ContantUtil.LOSSPWD_SENDVERIFY+"?userName="+et1.getText().toString()+"&phone="+et2.getText().toString();
			httpUtils.sendGetHttpRequest(url);
			timer=new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Message msg = Message.obtain();
					msg.what = SET_TV3;
					msg.obj = "发送失败,请检查网络";
					handler.sendMessage(msg);
					
					
				}
			}, 8000);
		}
	
	}

	@Override
	public void getData(boolean isSuccess, String content) {
		if(isSuccess){
			try {
				JSONObject jsonObject=new JSONObject(content);
				int opCode=jsonObject.getInt("opCode");
				if (opCode == 99) {
					Message msg = Message.obtain();
					msg.what = SET_TV3;
					msg.obj = jsonObject.getInt("opMess");
					handler.sendMessage(msg);
					
				} else if (opCode == 100) {
					Message msg = Message.obtain();
					msg.what = SET_TV3;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
					

				} else if (opCode == 101) {
					Message msg = Message.obtain();
					msg.what = SET_TV3;
					msg.obj = jsonObject.getString("opMess");
					handler.sendMessage(msg);
					
				} 
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
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

}
