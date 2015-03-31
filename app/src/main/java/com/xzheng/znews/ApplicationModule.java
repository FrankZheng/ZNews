package com.xzheng.znews;

import android.content.Context;

import com.xzheng.znews.adapter.ArticleListAdapter;
import com.xzheng.znews.library.model.LibraryModel;
import com.xzheng.znews.model.Article;
import com.xzheng.znews.service.ContentService;

import dagger.Module;
import dagger.Provides;

@Module(
		staticInjections = {
			Article.class,	
		},
		injects = {
				MainActivity.class,
				ArticleDetailActivity.class,
				ArticleListAdapter.class,
				LibraryModel.class,
				Article.class,
                ContentService.class
		}
)
public class ApplicationModule {
	@Provides 
	Context provideContext() {
		return MainApplication.getContext();
	}

}
