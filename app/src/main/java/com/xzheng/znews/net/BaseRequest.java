package com.xzheng.znews.net;

import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xzheng.znews.util.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxiaoqiang on 15/3/6.
 */
public abstract class BaseRequest {

    private static final String BASE_URL = "http://xnewsreader.herokuapp.com/";

    private static final String TAG = BaseRequest.class.getSimpleName();
    private final Logger mLogger = new Logger.Builder().tag(TAG).build();

    private String mRelativeUrl;
    private HashMap<String, String> mParams = new HashMap<String, String>();
    private HashMap<String, String> mQueryParams = new HashMap<>();
    private IResponseListener mResponseListener;




    private static class InnerRequest extends StringRequest {
        private Map<String, String> _params;

        public InnerRequest(int method, String url,
                         Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        public void setParams(Map<String, String> params) {
            _params = params;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return _params;
        }
    }

    public BaseRequest(String relativeUrl, IResponseListener listener) {

        mRelativeUrl = relativeUrl;
        mResponseListener = listener;

        //add common parameters
        addCommonParams();
    }

    protected String buildFullUrl() {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon().appendPath(mRelativeUrl);
        for(Map.Entry<String, String> entry : mQueryParams.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().toString();
    }

    protected int getMethod() {
        //default is POST method
        return Request.Method.GET;
    }

    public Request buildVolleyRequest() {
        String url = buildFullUrl();
        int method = getMethod();
        InnerRequest req = new InnerRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onVolleyResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onVolleyErrorResponse(error);
            }
        });

        //set params
        req.setParams(mParams);

        return req;
    }

    protected void onVolleyResponse(String responseStr) {
        mLogger.d("onVolleyResponse, " + responseStr);
        MyResponse response = new MyResponse();
        response.data = parseResponse(responseStr);
        mResponseListener.onResponse(response);
    }

    protected void onVolleyErrorResponse(VolleyError error) {
        NetError netError = new NetError(error);
        mResponseListener.onErrorResponse(netError);
    }

    protected Object parseResponse(String dataStr)  {
        return dataStr;
    }

    protected void addCommonParams() {
        //default add nothing here
    }

    protected void addParam(String name, String value) {
        if(!TextUtils.isEmpty(name)) {
            mParams.put(name, value == null ? "" : value);
        }
    }

    protected void addParam(String name, int value) {
        if(!TextUtils.isEmpty(name)) {
            mParams.put(name, String.valueOf(value));
        }
    }

    protected void addParam(String name, long value) {
        if(!TextUtils.isEmpty(name)) {
            mParams.put(name, String.valueOf(value));
        }
    }

    protected void addParam(String name, double value) {
        if(!TextUtils.isEmpty(name)) {
            mParams.put(name, String.valueOf(value));
        }
    }

    protected String getRelativeUrl() {
        return mRelativeUrl;
    }

    protected void addQueryParam(String name, String value) {
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
            mQueryParams.put(name, value);
        }
    }

    protected void addQueryParam(String name, int value) {
        if(!TextUtils.isEmpty(name)) {
            mQueryParams.put(name, String.valueOf(value));
        }
    }



}
