package com.hdljz.mobileplayer.domain;

/**
 * ��Ƶ��Ϣ
 * 
 * @author ������
 * 
 */
public class VideoInfo {

	private String name;

	private long duration;

	private long size;

	private String data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "VideoItem [name=" + name + ", duration=" + duration + ", size="
				+ size + ", data=" + data + "]";
	}

}
