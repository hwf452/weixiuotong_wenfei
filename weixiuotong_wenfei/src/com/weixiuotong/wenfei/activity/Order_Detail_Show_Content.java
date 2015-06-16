package com.weixiuotong.wenfei.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Order_Detail_Show_Content extends Activity {
	private TextView tv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_detail_show_content);
		tv1=(TextView)findViewById(R.id.textView1);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		String content_detail=bundle.getString("content_detail");
		tv1.setText(content_detail);
	}

}
