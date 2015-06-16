package com.weixiuotong.wenfei.user_activity;


//软件开启第一屏界面
import com.weixiuotong.wenfei.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class StartActivity extends Activity {
	private long m_dwSplashTime = 5000;
	private boolean m_bPaused = false;
	private boolean m_bSplashActive = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					long ms = 0;
					while (m_bSplashActive && ms < m_dwSplashTime) {
						sleep(100);
						if (!m_bPaused) {
							ms += 100;
						}
					}

					startActivity(new Intent(
							"com.weixiuotong.wenfei.activity.LOGINACTIVITY"));

				} catch (Exception e) {
					Log.e("splash", e.getMessage());
				} finally {
					finish();
				}

			}

		};
		splashTimer.start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			m_bSplashActive = false;

			break;
		case KeyEvent.KEYCODE_BACK:
			android.os.Process.killProcess(android.os.Process.myPid());

			break;
		default:
			break;
		}

		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		m_bPaused = true;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		m_bPaused = false;
	}


}
