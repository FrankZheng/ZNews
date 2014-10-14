package com.xzheng.znews.library.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.util.Log;
import android.widget.Toast;

import com.xzheng.znews.MainApplication;
import com.xzheng.znews.model.Article;
import com.xzheng.znews.signal.SignalFactory;
import com.xzheng.znews.signal.SignalFactory.SignalImpl;
import com.xzheng.znews.util.Logger;
import com.xzheng.znews.util.UrlFetcher;
import com.xzheng.znews.util.UrlFetcherHandler;


@Singleton
public class LibraryModel {
    private static final String LOG_TAG = "LibraryModel";
    private Logger _logger = new Logger.Builder().tag(LOG_TAG).build();

	private Map<String, Article> _articleMap;
	private final SignalImpl<Boolean> _updateDoneSignal;

	private List<Article> _articleList = new ArrayList<Article>();
	
	@Inject
	public LibraryModel(SignalFactory signalFactory) {
		
		_updateDoneSignal = signalFactory.createSignal();
	}
	
	private final Comparator<Article> _articleComparator = new Comparator<Article>() {

		@Override
		public int compare(Article lhs, Article rhs) {
			int result;
			Date lDate = lhs.getPubDate();
			Date rDate = rhs.getPubDate();
			if (lDate == null && rDate == null) {
				result = 0;
			} else if (lDate == null) {
				// We'll consider null dates as "newer" than any other date
				result = -1;
			} else if (rDate == null) {
				result = 1;
			} else {
				// Reverse date order
				result = -lDate.compareTo(rDate);
			}
			return result;
		}
		
	};
	
	public List<Article> getArticleList() {
		List<Article> list = new ArrayList<Article>(getArticleMap().values());
		//sort the article
		Collections.sort(list, _articleComparator);
		if(!_articleList.equals(list)) {
			//the _articleList will be passed to ArticleListAdapter, which will be retained
			//so here should NOT assign it the new value.
			_articleList.clear();
			_articleList.addAll(list);
		}
		return _articleList;
	}
	
	public synchronized Map<String, Article> getArticleMap() {
		if(_articleMap == null) {
			_articleMap = new HashMap<String, Article>();
			//load articles from db
			try {
				List<Article> savedArticles = Article.getDao().queryForAll();
				for(Article article : savedArticles) {
					_articleMap.put(article.getId(), article);
				}
			} catch (SQLException e) {
				_logger.e(e, "failed to load articles from db");
				//print stack trace may give more info to help debug issue
				//e.printStackTrace();
			}
		}
		return _articleMap;
	}
	
	public SignalImpl<Boolean> getUpdateDoneSignal() {
		return _updateDoneSignal;
	}
	
	public void update() {
		//get article list from server.
		_logger.i("update");
		
		final String urlString = "http://xnewsreader.herokuapp.com/articles?limit=20&topic=t&output=json";
		
		try {
			URL url = new URL(urlString);
			new UrlFetcher(_handler).execute(url);
		} catch (MalformedURLException e) {
			_logger.e(e);
		}
	}
	
	private UrlFetcherHandler _handler = new UrlFetcherHandler() {

		@Override
		public void onResult(Object result) {
			_logger.i("on articles back");
			String data = (String) result;
			Object object;
			
			if(data == null) {
				_logger.e("return null data");
				_updateDoneSignal.dispatch(false);
				return;
			}
			
			try {
				object = new JSONTokener(data).nextValue();
				if(object instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray)object;
					int added = 0;
					for (int i = 0, length = jsonArray.length(); i < length; i++) {
						Article article = new Article(jsonArray.getJSONObject(i));
						if(!_articleMap.containsKey(article.getId())) {
							_articleMap.put(article.getId(), article);
							//persist to db
							article.persist();
							added++;
						}
					}
					//show messages
					String message = added > 0 ? String.format("Has %d new articles", added) : "No new articles";
					Toast.makeText(MainApplication.getContext(), message, Toast.LENGTH_SHORT).show();
					
					//update signal
					_updateDoneSignal.dispatch(added > 0);
					
				}
				
			} catch (JSONException e) {
				_logger.e(e);
				
			} catch (SQLException e) {
				_logger.e(e);
				
			}
		}
	};
	
}
