package com.xzheng.znews;

import android.content.Context;

import com.xzheng.znews.adapter.ArticleListAdapter;
import com.xzheng.znews.library.model.LibraryModel;
import com.xzheng.znews.model.Article;

import dagger.Module;
import dagger.Provides;

@Module(
		staticInjections = {
			Article.class,	
		},
		injects = {
				MainActivity.class,
				DisplayArticleActivity.class,
				ArticleListAdapter.class,
				LibraryModel.class,
				Article.class,
		}
)
public class ApplicationModule {
	@Provides 
	Context provideContext() {
		return MainApplication.getContext();
	}

}
