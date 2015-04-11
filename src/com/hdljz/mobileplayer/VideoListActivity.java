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
 * 视频列表
 * 
 * @author 梁建桢
 * 
 */
public class VideoListActivity extends Activity {

	private ListView lv_videolist;
	private TextView lv_videolist_novideo;

	private Utils utils;

	/**
	 * 视频列表
	 */
	private ArrayList<VideoInfo> videoItems;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (videoItems != null && videoItems.size() > 0) {
				// 有视频信息
				lv_videolist_novideo.setVisibility(View.GONE);

				// 显示在ListView中
				lv_videolist.setAdapter(new VideoListAdapter());
			} else {
				// 没有视频信息
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

				// 根据位置得到对应的数据
				VideoInfo item = videoItems.get(position);
				// Toast.makeText(getApplicationContext(), item.getName(),
				// 1).show();
				// 把手机里面所有的播放器调起来-隐式意图
				// Intent intent = new Intent();
				// intent.setAction(Intent.ACTION_VIEW);
				// intent.setDataAndType(Uri.parse(item.getData()), "video/*");
				// startActivity(intent);
				//

				// 利用自定义播放器
				Intent intent = new Intent(VideoListActivity.this,
						VideoPlayerActivity.class);
				intent.setData(Uri.parse(item.getData()));
				startActivity(intent);

			}
		});

	}

	class VideoListAdapter extends BaseAdapter {

		// 返回总条数
		@Override
		public int getCount() {
			return videoItems.size();
		}

		/**
		 * 没看到的一条View
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			// 有历史缓存就用历史的
			ViewHolder holder;
			if (convertView != null) {
				view = convertView;
				System.out.println("使用历史缓存的View" + position);
				holder = (ViewHolder) view.getTag();
			} else {
				System.out.println("创建新的View" + position);
				view = View.inflate(VideoListActivity.this,
						R.layout.videolist_item, null);
				holder = new ViewHolder();
				// 查找View的ID也是消耗资源的，当创建View的时候，把查找的放在一个容器（类）
				// 实例化孩子
				holder.tv_videolist_name = (TextView) view
						.findViewById(R.id.tv_videolist_name);
				holder.tv_videolist_duration = (TextView) view
						.findViewById(R.id.tv_videolist_duration);
				holder.tv_videolist_size = (TextView) view
						.findViewById(R.id.tv_videolist_size);

				// 把容器和View 关系保存起来
				view.setTag(holder);

			}
			// 把布局文件实例化-->View对象

			// 根据位置得到对应的视频信息
			VideoInfo item = videoItems.get(position);
			holder.tv_videolist_name.setText(item.getName());// 设置名称
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
	 * 得到手机的视频
	 */
	private void getData() {
		new Thread() {
			public void run() {

				videoItems = new ArrayList<VideoInfo>();

				// 视频的路径
				Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				String[] projection = { MediaStore.Video.Media.DISPLAY_NAME,// 视频的名称
						MediaStore.Video.Media.DURATION,// 视频的时长
						MediaStore.Video.Media.SIZE,// 视频的大小
						MediaStore.Video.Media.DATA,// 视频的在sdcard下的绝对路径
				};
				// 读取手机里面所有的视频
				Cursor cursor = getContentResolver().query(uri, projection,
						null, null, null);
				while (cursor.moveToNext()) {

					VideoInfo item = new VideoInfo();

					String name = cursor.getString(0);// 视频的名称
					item.setName(name);
					long duration = cursor.getLong(1);// 视频长度
					item.setDuration(duration);
					long size = cursor.getLong(2);// 视频的大小
					item.setSize(size);
					String data = cursor.getString(3);// 视频的播放地址
					item.setData(data);

					// 放入视频列表中
					videoItems.add(item);

				}

				cursor.close();// 关闭

				// 发消息到主线程中显示数据
				handler.sendEmptyMessage(1);

			};
		}.start();

	}

	/**
	 * 初始化View
	 */
	private void initView() {
		utils = new Utils();
		setContentView(R.layout.videolist_activity);
		lv_videolist = (ListView) findViewById(R.id.lv_videolist);
		lv_videolist_novideo = (TextView) findViewById(R.id.lv_videolist_novideo);
	}

}
