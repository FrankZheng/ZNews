package com.xzheng.znews.net;

import com.alibaba.fastjson.JSON;
import com.xzheng.znews.configuration.Topic;
import com.xzheng.znews.model.ArticleDescriptor;

/**
 * Created by zhengxiaoqiang on 15/3/31.
 */
public class GetArticlesRequest extends BaseRequest {
    private static final String RELATIVE_URL = "articles";

    private static final String LANGUAGE_KEY = "lang";
    private static final String LIMIT_KEY = "limit";
    private static final String TOPIC_KEY = "topic";
    private static final String BEFORE_KEY = "before";
    private static final String OUTPUT_KEY = "output";

    public GetArticlesRequest(IResponseListener listener) {
        super(RELATIVE_URL, listener);
    }

    public GetArticlesRequest(int limit, Topic topic, IResponseListener listener) {
        this(listener);

        addQueryParam(LIMIT_KEY, limit);
        addQueryParam(TOPIC_KEY, topic.toString());
    }

    public GetArticlesRequest(int limit, Topic topic, String dateStr, IResponseListener listener) {
        this(listener);
        addQueryParam(LIMIT_KEY, limit);
        addQueryParam(TOPIC_KEY, topic.toString());
        addQueryParam(BEFORE_KEY, dateStr);
    }

    @Override
    protected Object parseResponse(String dataStr) {
        return JSON.parseArray(dataStr, ArticleDescriptor.class);
    }
}
