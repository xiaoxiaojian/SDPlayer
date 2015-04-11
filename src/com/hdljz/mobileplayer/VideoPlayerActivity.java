package com.hdljz.mobileplayer;

import com.hdljz.mobileplayer.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * 视频播放器
 * 
 * @author 梁建桢
 * 
 */
public class VideoPlayerActivity extends Activity {

	private VideoView videoview;
	/**
	 * 视频播放地址
	 */
	private Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videopalyer_activity);
		videoview = (VideoView) findViewById(R.id.videoview);
		uri = getIntent().getData();

		videoview.setVideoURI(uri);
		// 设置一下监听：播放完成的监听，播放准备好的监听，播放出错的监听
		videoview.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				// 开始播放
				videoview.start();
			}
		});

		videoview.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(getApplicationContext(), "视频播放完成了", 1).show();
				finish();// 退出播放器
			}
		});

		videoview.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				Toast.makeText(getApplicationContext(), "视频播放出错了", 1).show();
				return true;
			}
		});
		// 设置控制视频的控制面板
		videoview.setMediaController(new MediaController(this));
	}

}
