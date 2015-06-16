package com.weixiuotong.wenfei.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Repair_Order_Already_Activity extends Activity implements OnClickListener {
	private Button btn1,btn2;
	
	private TextView tv4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair_order_already_submit);
		btn1=(Button)findViewById(R.id.button1);
		btn2=(Button)findViewById(R.id.button2);
		tv4=(TextView)findViewById(R.id.textView4);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		Intent intent=getIntent();
		String string=intent.getStringExtra("subNum");
		tv4.setText(string);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
			
			break;

		case R.id.button2:
			
			MainActivity.getInstance().tabHost.setCurrentTab(2);
			MainActivity.getInstance().rb3.setChecked(true);
			finish();
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
			break;
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
