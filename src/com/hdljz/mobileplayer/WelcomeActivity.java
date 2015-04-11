package com.hdljz.mobileplayer;

import com.hdljz.mobileplayer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;

/**
 * ��ӭҳ��
 * 
 * @author ������
 * 
 */
public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// �������ر�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startVideoList();
			}
		}, 2000);
	}

	/**
	 * �Ƿ��Ѿ���������Ƶ�б� true:������ false��û������
	 */
	private boolean isStartVideoList = false;

	/**
	 * ������Ƶ�б�
	 */
	private void startVideoList() {
		if (!isStartVideoList) {
			isStartVideoList = true;
			Intent intent = new Intent(this, VideoListActivity.class);
			startActivity(intent);
			// �رյ�ǰ��ӭҳ��
			finish();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		startVideoList();
		return super.onTouchEvent(event);
	}

}
