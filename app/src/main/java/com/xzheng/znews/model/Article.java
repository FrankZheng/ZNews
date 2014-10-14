package com.xzheng.znews.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.json.JSONObject;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xzheng.znews.MainApplication;
import com.xzheng.znews.persistence.DatabaseHelper;
import com.xzheng.znews.util.Logger;

@DatabaseTable(tableName = "articles")
public class Article implements Serializable{
	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private static final String LOG_TAG = "Article";

    private transient Logger _logger = new Logger.Builder().tag(LOG_TAG).build();
	
	@Inject
	static DatabaseHelper _databaseHelper;
	
	@DatabaseField(id = true)
	private String _id;
	
	@DatabaseField
	private String _title;
	
	@DatabaseField
	private String _link;
	
	@DatabaseField
	private String _publisher;
	
	@DatabaseField
	private String _thumb;
	
	@DatabaseField
	private String _brief;
	
	@DatabaseField
	private Date _pubDate;
	
	@DatabaseField
	private String _text;
	
	private static Dao<Article, String> _dao;
	
	private static final DateFormat _dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			Locale.US);
	
	/**
	 * No-Arg constructor that is used by OrmLite
	 */
	public Article() {
		MainApplication.getApplication().inject(this);
	}
	
	public Article(JSONObject json) {
		_id = json.optString("_id");
		_title = json.optString("title");
		_link = json.optString("link");
		_publisher = json.optString("publisher");
		_thumb = json.optString("thumb");
		_brief = json.optString("brief");
		
		try {
			String dateStr = json.optString("pubDate");
			String str = dateStr.substring(0, dateStr.length()-1) + "GMT";
			_pubDate = _dateParser.parse(str);
		} catch (ParseException e) {
			//ignore and continue, may need current date later
			_logger.e(e, "Failed to parse the date string");
		}
		 
	}
	
	public String getId() {
		return _id;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public String getLink() {
		return _link;
	}
	
	public String getPublisher() {
		return _publisher;
	}
	
	public String getThumb() {
		return _thumb;
	}
	
	public String getBrief() {
		return _brief;
	}
	
	public Date getPubDate() {
		return _pubDate;
	}
	
	public String getText() {
		return _text;
	}
	
	public void setText(String text) {
		_text = text;
	}
	
	public static Dao<Article, String> getDao() throws SQLException {
		if(_dao == null) {
			_dao = _databaseHelper.createDao(Article.class, String.class); 
		}
		return _dao;
	}
	
	public void persist() throws SQLException {
		//It may add lock when query the records from DB
		Article.getDao().createOrUpdate(this);
	}
	
	
	
	

}
