package com.xzheng.znews.service;


import android.net.Uri;

import com.google.common.base.Strings;
import com.xzheng.znews.MainApplication;
import com.xzheng.znews.configuration.Configuration;
import com.xzheng.znews.configuration.Topic;
import com.xzheng.znews.model.Article;
import com.xzheng.znews.util.HttpUtil;
import com.xzheng.znews.util.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xzheng on 10/9/14.
 */
@Singleton
public class ContentService {
    private static final String BASE_URL = "http://xnewsreader.herokuapp.com";
    private static final String FMT_JSON = "json";
    private Logger _logger = new Logger.Builder().tag("ContentService").build();

    @Inject
    HttpUtil _httpUtil;

    @Inject
    public ContentService() {
        //Enable injected dependencies
        MainApplication.getApplication().inject(this);
    }

    public List<Article> getArticles(Topic topic, int limit) {
        //Form the url
        Uri.Builder builder  = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("articles")
                .appendQueryParameter("limit", String.valueOf(limit))
                .appendQueryParameter("output", FMT_JSON);

        if(!Strings.isNullOrEmpty(topic.toString())) {
            builder.appendQueryParameter("topic", topic.toString());
        }


        String url = builder.build().toString();

        //Fetch and parse json data from the content server
        List<Article> articles = new ArrayList<Article>();
        String data = null;
        try {
            data = _httpUtil.get(url);
            if(!Strings.isNullOrEmpty(data)) {
                Object object = new JSONTokener(data).nextValue();
                if(object instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) object;
                    for (int i = 0, length = jsonArray.length(); i < length; i++) {
                        Article article = new Article(jsonArray.getJSONObject(i));
                        articles.add(article);
                    }
                }
            }
        } catch (IOException e) {
            _logger.e(e, "Failed to fetch data from %s", url);
        } catch (JSONException ex) {
            _logger.e(ex, "Failed to parse json data %s", data);
        }
        return articles;
    }
}
