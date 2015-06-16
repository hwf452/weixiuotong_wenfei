package com.weixiuotong.wenfei.activity;

import android.app.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About_Me_Activity extends Activity implements OnClickListener {
	private Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_me_activity);
		btn1=(Button)findViewById(R.id.button1);
		btn1.setOnClickListener(this);
				
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			
			finish();
			overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);  
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
