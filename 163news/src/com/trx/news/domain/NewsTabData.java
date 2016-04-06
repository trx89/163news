package com.trx.news.domain;

import java.util.ArrayList;

public class NewsTabData {

	public NewsData data;
	public int retcode;
	
	public class NewsData{
		public String more; //更多数据url地址
		public ArrayList<NewsNormal> news;
		public ArrayList<NewsTop> topnews;
		public String title;
	}
	
	public class NewsNormal{
		public String listimage;
		public String pubdate;
		public String title;
		public String url;
		public String id;
	}
	
	public class NewsTop{
		public String topimage;
		public String title;
		public String url;
	}
}
