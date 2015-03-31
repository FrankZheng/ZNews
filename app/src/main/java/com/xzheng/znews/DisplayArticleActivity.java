package com.xzheng.znews;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.common.base.Strings;
import com.xzheng.znews.model.Article;
import com.xzheng.znews.util.AssetUtil;
import com.xzheng.znews.util.Logger;
import com.xzheng.znews.util.UrlFetcher;
import com.xzheng.znews.util.UrlFetcherHandler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class DisplayArticleActivity extends BaseActivity {
	
	@Inject
	AssetUtil _assetUtil;
	
	protected final String LOG_TAG = "DisplayArticleActivity";
    private Logger _logger = new Logger.Builder().tag(LOG_TAG).build();
	private WebView _webView;
	static private String _articleHtmlFmt;
	private Article _article;
	
	public DisplayArticleActivity() {
		super();
		
		// Bootstrap the Injection process starting from this activity
		MainApplication.getApplication().inject(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setNeedBackGesture(true);

        getActionBar().setDisplayHomeAsUpEnabled(true);
		
		setContentView(R.layout.activity_display_article);
		_webView = (WebView)this.findViewById(R.id.webview);
		//set default font size, does NOT work
		//use css to style text later
		//use web view to display article is simple.
		//but need consider the select word issue.
		//later need figure out a solution.
		//final WebSettings webSettings = _webView.getSettings();
		//webSettings.setDefaultFixedFontSize(20);
		
		if(_articleHtmlFmt == null) {
			_articleHtmlFmt = _assetUtil.ReadFromfile("article.html", getApplicationContext());
		}
		
		
		UrlFetcherHandler handler = new UrlFetcherHandler() {

			@Override
			public void onResult(Object result) {
				_logger.i("on artile text back");
				String data = (String) result;
				Object object;
				try {
					object = new JSONTokener(data).nextValue();
					if(object instanceof JSONObject) {
						JSONObject json = (JSONObject)object;
						String text = json.optString("text");
						if(_article != null) {
							_article.setText(text);
							_article.persist();
						}
						populateArticleText(text);
					}
					
				} catch (JSONException e) {
					_logger.e(e);
				} catch (SQLException ex) {
					_logger.e(ex, "Failed to save article text into db");
				}
				
			}
			
		};
		
		Intent intent = getIntent();
		_article = (Article)intent.getSerializableExtra("article");
		if(_article != null && !Strings.isNullOrEmpty(_article.getText())) {
			//text is already fetched, show it directly
			populateArticleText(_article.getText());
		} else {
			//fetch the text at first
			final String urlString = String.format("http://xnewsreader.herokuapp.com/article/%s?output=json", _article.getId());
			
			try {
				URL url = new URL(urlString);
				new UrlFetcher(handler).execute(url);
			} catch (MalformedURLException e) {
				_logger.e(e);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_article, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	private void populateArticleText(String text) {
		
		String[] lines = text.split("[\r\n]+");
		String bodyString = "";
		for(String line : lines) {
			bodyString += "<p>" + line + "</p>";
		}
		String html = String.format(_articleHtmlFmt, bodyString);
		_webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
