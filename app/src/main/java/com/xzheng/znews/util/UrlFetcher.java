package com.xzheng.znews.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class UrlFetcher extends AsyncTask<URL, Integer, String>{
	
	protected static final String LOG_TAG = "UrlFetcher"; 
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
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader
					(is, "UTF-8") );
			String data = null;
			String webPage = "";
			while ((data = reader.readLine()) != null){
				webPage += data + "\n";
			}
			return webPage;
		} catch(Exception e) {
			Log.e(LOG_TAG, "Failed to fetch url " + e.toString());
			
			//TODO: It will cause issues to make toast in background thread
			//figure out a way to throw the exception out.
			//Toast.makeText(context, "Failed to fetch feeds, " + e.toString(), Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	@Override
	protected void onPostExecute(String body){
		//body should be json format
		handler.onResult(body);
	}

	
}
