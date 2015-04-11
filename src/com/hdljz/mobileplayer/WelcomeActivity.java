package com.hdljz.mobileplayer;

import com.hdljz.mobileplayer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;

/**
 * 欢迎页面
 * 
 * @author 梁建桢
 * 
 */
public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置隐藏标题栏
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
	 * 是否已经启动的视频列表 true:启动了 false：没有启动
	 */
	private boolean isStartVideoList = false;

	/**
	 * 启动视频列表
	 */
	private void startVideoList() {
		if (!isStartVideoList) {
			isStartVideoList = true;
			Intent intent = new Intent(this, VideoListActivity.class);
			startActivity(intent);
			// 关闭当前欢迎页面
			finish();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		startVideoList();
		return super.onTouchEvent(event);
	}

}
