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
 * ��Ƶ������
 * 
 * @author ������
 * 
 */
public class VideoPlayerActivity extends Activity {

	private VideoView videoview;
	/**
	 * ��Ƶ���ŵ�ַ
	 */
	private Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// �������ر�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videopalyer_activity);
		videoview = (VideoView) findViewById(R.id.videoview);
		uri = getIntent().getData();

		videoview.setVideoURI(uri);
		// ����һ�¼�����������ɵļ���������׼���õļ��������ų���ļ���
		videoview.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				// ��ʼ����
				videoview.start();
			}
		});

		videoview.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(getApplicationContext(), "��Ƶ���������", 1).show();
				finish();// �˳�������
			}
		});

		videoview.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				Toast.makeText(getApplicationContext(), "��Ƶ���ų�����", 1).show();
				return true;
			}
		});
		// ���ÿ�����Ƶ�Ŀ������
		videoview.setMediaController(new MediaController(this));
	}

}
