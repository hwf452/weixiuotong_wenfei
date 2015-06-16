package com.weixiuotong.wenfei.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class Submit_ShowPicture_Activity extends Activity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.submit_showpicture_activity);
		imageView=(ImageView)findViewById(R.id.imageView1);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		Bitmap bitmap=(Bitmap) bundle.get("bitmap");
		imageView.setImageBitmap(bitmap);
	}

}
