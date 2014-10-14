package com.xzheng.znews.util;


import java.net.URL;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class UrlFetcher extends AsyncTask<URL, Integer, String>{
	
	protected static final String LOG_TAG = "UrlFetcher";
    private Logger _logger = new Logger.Builder().tag(LOG_TAG).build();

	private UrlFetcherHandler handler;
	public UrlFetcher(UrlFetcherHandler handler) {
		this.handler = handler;
	}

	protected void onPreExecute(){
		
	}
	@Override
	protected String doInBackground(URL... urls) {
		try{

            URL url = urls[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();
            return response.body().string();

		} catch(Exception e) {
			_logger.e(e, "Failed to fetch url");
			return null;
		}
	}
	@Override
	protected void onPostExecute(String body){
		//body should be json format
		handler.onResult(body);
	}

	
}
