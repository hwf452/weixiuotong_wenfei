package com.weixiuotong.wenfei.activity;



import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//修改个人资料
public class Modify_PrivateActivity extends Activity implements OnClickListener{
	private Button btn1,btn2;

	private EditText et1,et2;


	public static final int RESULTCODE_USE_NEW_ADDRESS=1;
	private TextView tv6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.modify_private_imformation);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		
		tv6=(TextView)findViewById(R.id.textView6);
		
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			
			
			
			break;

		case R.id.button2:
			modify_submit_address();
			
			break;
		}
		
	}


	private void modify_submit_address() {
		if(et1.getText().toString().equals("")){
			tv6.setText("姓名不能为空!");
			return;
		}
		if(et2.getText().toString().equals("")){
			tv6.setText("手机不能为空!");
			return;
		}
		
		Intent intent = new Intent();
		  
		intent.putExtra("name",et1.getText().toString());
		intent.putExtra("Phone",et2.getText().toString());
		
		setResult(RESULTCODE_USE_NEW_ADDRESS, intent);
		finish();
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		
		
	}



}
