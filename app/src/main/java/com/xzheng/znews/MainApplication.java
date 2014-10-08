package com.xzheng.znews;

import java.util.Arrays;
import java.util.List;


import dagger.ObjectGraph;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
	private static Context context;
	private ObjectGraph graph;
	private static MainApplication application;
	
	public void onCreate() {
		super.onCreate();
		
		application = this;
		MainApplication.context = getApplicationContext();
		
		// Initialize the Object Graph used by Dagger
		graph = ObjectGraph.create(getModules().toArray());
		
		// Inject all static properties
		graph.injectStatics();

	}
	
	public static Context getContext() {
		return context;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	
	protected List<Object> getModules() {
		return Arrays.<Object> asList(new ApplicationModule());
	}
	
	public void inject(Object object) {
		graph.inject(object);
	}
	
}
