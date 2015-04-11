package com.hdljz.mobileplayer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hdljz.mobileplayer.R;
import com.hdljz.mobileplayer.domain.VideoInfo;
import com.hdljz.mobileplayer.utils.Utils;

/**
 * ��Ƶ�б�
 * 
 * @author ������
 * 
 */
public class VideoListActivity extends Activity {

	private ListView lv_videolist;
	private TextView lv_videolist_novideo;

	private Utils utils;

	/**
	 * ��Ƶ�б�
	 */
	private ArrayList<VideoInfo> videoItems;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (videoItems != null && videoItems.size() > 0) {
				// ����Ƶ��Ϣ
				lv_videolist_novideo.setVisibility(View.GONE);

				// ��ʾ��ListView��
				lv_videolist.setAdapter(new VideoListAdapter());
			} else {
				// û����Ƶ��Ϣ
				lv_videolist_novideo.setVisibility(View.VISIBLE);
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		getData();
		setListener();
	}

	private void setListener() {
		lv_videolist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ����λ�õõ���Ӧ������
				VideoInfo item = videoItems.get(position);
				// Toast.makeText(getApplicationContext(), item.getName(),
				// 1).show();
				// ���ֻ��������еĲ�����������-��ʽ��ͼ
				// Intent intent = new Intent();
				// intent.setAction(Intent.ACTION_VIEW);
				// intent.setDataAndType(Uri.parse(item.getData()), "video/*");
				// startActivity(intent);
				//

				// �����Զ��岥����
				Intent intent = new Intent(VideoListActivity.this,
						VideoPlayerActivity.class);
				intent.setData(Uri.parse(item.getData()));
				startActivity(intent);

			}
		});

	}

	class VideoListAdapter extends BaseAdapter {

		// ����������
		@Override
		public int getCount() {
			return videoItems.size();
		}

		/**
		 * û������һ��View
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			// ����ʷ���������ʷ��
			ViewHolder holder;
			if (convertView != null) {
				view = convertView;
				System.out.println("ʹ����ʷ�����View" + position);
				holder = (ViewHolder) view.getTag();
			} else {
				System.out.println("�����µ�View" + position);
				view = View.inflate(VideoListActivity.this,
						R.layout.videolist_item, null);
				holder = new ViewHolder();
				// ����View��IDҲ��������Դ�ģ�������View��ʱ�򣬰Ѳ��ҵķ���һ���������ࣩ
				// ʵ��������
				holder.tv_videolist_name = (TextView) view
						.findViewById(R.id.tv_videolist_name);
				holder.tv_videolist_duration = (TextView) view
						.findViewById(R.id.tv_videolist_duration);
				holder.tv_videolist_size = (TextView) view
						.findViewById(R.id.tv_videolist_size);

				// ��������View ��ϵ��������
				view.setTag(holder);

			}
			// �Ѳ����ļ�ʵ����-->View����

			// ����λ�õõ���Ӧ����Ƶ��Ϣ
			VideoInfo item = videoItems.get(position);
			holder.tv_videolist_name.setText(item.getName());// ��������
			holder.tv_videolist_size.setText(Formatter.formatFileSize(
					VideoListActivity.this, item.getSize()));
			holder.tv_videolist_duration.setText(utils.stringForTime((int) item
					.getDuration()));

			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	static class ViewHolder {
		TextView tv_videolist_name;
		TextView tv_videolist_duration;
		TextView tv_videolist_size;
	}

	/**
	 * �õ��ֻ�����Ƶ
	 */
	private void getData() {
		new Thread() {
			public void run() {

				videoItems = new ArrayList<VideoInfo>();

				// ��Ƶ��·��
				Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				String[] projection = { MediaStore.Video.Media.DISPLAY_NAME,// ��Ƶ������
						MediaStore.Video.Media.DURATION,// ��Ƶ��ʱ��
						MediaStore.Video.Media.SIZE,// ��Ƶ�Ĵ�С
						MediaStore.Video.Media.DATA,// ��Ƶ����sdcard�µľ���·��
				};
				// ��ȡ�ֻ��������е���Ƶ
				Cursor cursor = getContentResolver().query(uri, projection,
						null, null, null);
				while (cursor.moveToNext()) {

					VideoInfo item = new VideoInfo();

					String name = cursor.getString(0);// ��Ƶ������
					item.setName(name);
					long duration = cursor.getLong(1);// ��Ƶ����
					item.setDuration(duration);
					long size = cursor.getLong(2);// ��Ƶ�Ĵ�С
					item.setSize(size);
					String data = cursor.getString(3);// ��Ƶ�Ĳ��ŵ�ַ
					item.setData(data);

					// ������Ƶ�б���
					videoItems.add(item);

				}

				cursor.close();// �ر�

				// ����Ϣ�����߳�����ʾ����
				handler.sendEmptyMessage(1);

			};
		}.start();

	}

	/**
	 * ��ʼ��View
	 */
	private void initView() {
		utils = new Utils();
		setContentView(R.layout.videolist_activity);
		lv_videolist = (ListView) findViewById(R.id.lv_videolist);
		lv_videolist_novideo = (TextView) findViewById(R.id.lv_videolist_novideo);
	}

}
