package com.xzheng.znews;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xzheng.znews.adapter.ArticleListAdapter;
import com.xzheng.znews.library.model.LibraryModel;
import com.xzheng.znews.model.Article;
import com.xzheng.znews.signal.Signal;

public class MainActivity extends Activity {
	public static final String LOG_TAG = "MainActivity";
	
	@Inject
	LibraryModel libraryModel;
	
	private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
	private ArticleListAdapter articleListAdapter;
	private boolean libraryLoaded = false;
	
	private Signal.Handler<Boolean> libraryUpdateDoneHandler = new Signal.Handler<Boolean>() {

		@Override
		public void onDispatch(Boolean changed) {
            swipeRefreshLayout.setRefreshing(false);
			libraryLoaded = true;
		}
	};
	
	private OnRefreshListener onRefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			libraryModel.update();
		}
	};
	
	private ListView.OnItemClickListener onListViewItemClickListener = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> listView, View v, int position,
				long id) {
			Article item = (Article) listView.getAdapter().getItem(position);
			//Toast.makeText(this, item.title + " selected", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
			intent.putExtra("article", item);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		}
	};
	
	public MainActivity() {
		super();
		
		// Bootstrap the Injection process starting from this activity
		MainApplication.getApplication().inject(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//setup pull refresh list view
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.main_refresh_layout);
		
		listView = (ListView)findViewById(R.id.main_list_view);
		articleListAdapter = new ArticleListAdapter(this, libraryModel.getArticleList());
        listView.setAdapter(articleListAdapter);
		
		
		//build default display image options for UIL
		DisplayImageOptions displayimageOptions = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.build();

	    // Create global configuration and initialize ImageLoader with this configuration
	    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
	            defaultDisplayImageOptions(displayimageOptions).build();
	    ImageLoader.getInstance().init(config);
	    
	    // Add library update done signal
	    libraryModel.getUpdateDoneSignal().add(libraryUpdateDoneHandler);
	    // Set a listener to be invoked when the list should be refreshed.
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
	 	// Set a click listener for list view
	 	listView.setOnItemClickListener(onListViewItemClickListener);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onStart() {
		super.onStart();
		if(!libraryLoaded) {
			
			//update library
			swipeRefreshLayout.setRefreshing(true);
			libraryModel.update();
		}
	}

}
