package com.xzheng.znews.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xzheng on 10/9/14.
 */
@Singleton
public class HttpUtil {

    @Inject
    public HttpUtil() {

    }

    public String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
