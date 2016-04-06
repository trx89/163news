package com.trx.news.domain;

import java.util.ArrayList;


public class NewsMenuData{
	public ArrayList<NewsMenuDetailData> data;
	public ArrayList<String> extend;
	public int retcode;
	
	@Override
	public String toString() {
		return "NewsMenuData [data=" + data + "]";
	}
	
	public class NewsMenuDetailData{
		public String id;
		public String title;
		public int type;
		public ArrayList<NewsMenuTabData> children;
		
		@Override
		public String toString() {
			return "NewsMenuDetailData [id=" + id + ", title=" + title
					+ ", type=" + type + ", children=" + children + "]";
		}

	}

	public class NewsMenuTabData{
		public String id;
		public String title;
		public int type;
		public String url;
		
		@Override
		public String toString() {
			return "NewsMenuTabData [id=" + id + ", title=" + title + ", type="
					+ type + ", url=" + url + "]";
		}
		
		
	}

}


