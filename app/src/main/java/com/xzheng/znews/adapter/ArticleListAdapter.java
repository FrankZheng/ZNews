package com.xzheng.znews.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xzheng.znews.MainApplication;
import com.xzheng.znews.R;
import com.xzheng.znews.library.model.LibraryModel;
import com.xzheng.znews.model.Article;
import com.xzheng.znews.signal.Signal;

public class ArticleListAdapter extends ArrayAdapter<Article>{
	
	@Inject
	LibraryModel libraryModel;
	
	private final Context context;
	private List<Article> articles;
	
	
	private Signal.Handler<Boolean> updateDoneSignal = new Signal.Handler<Boolean>() {

		@Override
		public void onDispatch(Boolean changed) {
			if(changed) {
				//when libraryModel changed, refresh the list
				ArticleListAdapter.this.articles = libraryModel.getArticleList();
				ArticleListAdapter.this.notifyDataSetChanged();
			}
		}
	};
	
	private static final SimpleDateFormat _dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

	public ArticleListAdapter(Context context, List<Article> values) {
		super(context, R.layout.rowlayout, values);
		
		MainApplication.getApplication().inject(this);
		
		this.context = context;
		this.articles = values;
		
		libraryModel.getUpdateDoneSignal().add(updateDoneSignal);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if(rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		}
		TextView textView = (TextView) rowView.findViewById(R.id.title);
		Article article = articles.get(position); 
		textView.setText(article.getTitle());
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.thumb);
		
		boolean useProxy = true;
		String thumbUrl = article.getThumb();
		if(useProxy) {
			thumbUrl = Uri.parse("http://xnewsreader.herokuapp.com/thumb")
	                .buildUpon()
	                .appendQueryParameter("thumburl", article.getThumb())
	                .build().toString();
		}
		
		//This will clean the thumb for new articles
		//It may not the best solution
		//Another way is clean image view if thumbUrl has NOT been cached
		if(!thumbUrl.equals(imageView.getTag())) {
			imageView.setImageDrawable(null); //clean the image
		}
		ImageLoader.getInstance().displayImage(thumbUrl, imageView);
		imageView.setTag(thumbUrl);
		    
		TextView dateView = (TextView) rowView.findViewById(R.id.pubDate);
		Date date = article.getPubDate();
		if(date != null) {
			dateView.setText(_dateFormater.format(date));
		}
		
		TextView publisherView = (TextView) rowView.findViewById(R.id.publisher);
		publisherView.setText(article.getPublisher());
		
		return rowView;
	}
}
