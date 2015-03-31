package com.xzheng.znews.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;

/**
 * Created by zhengxiaoqiang on 15/3/10.
 */
public class NetError {

    public static final int GENERIC_ERROR = 0;
    public static final int API_ERROR = 1;
    public static final int SERVER_ERROR = 2;
    public static final int NETWORK_ERROR = 3;
    public static final int JSON_PARSE_ERROR = 4;

    public int type;
    public int code;
    public String msg;
    public Exception exception;



    public NetError(VolleyError error) {
        //TODO: give it more formalized string, defined in Strings.xml later

        if(isNetworkProblem(error)) {
            type = NETWORK_ERROR;
            msg = "No Internet Connection";
        } else if(isServerProblem(error)) {
            type = SERVER_ERROR;
            //get status code
            NetworkResponse response = error.networkResponse;
            code = response.statusCode;
            msg = "Server Problem, status code - " + code;
        } else {
            type = GENERIC_ERROR;
            msg = "A generic error";
        }
    }

    public NetError(int _code, String _msg) {
        type = API_ERROR;
        code = _code;
        msg = _msg;
    }

    public NetError(NetError error) {
        type = error.type;
        code = error.code;
        msg = error.msg;
    }

    public NetError(int _code, String _msg, Exception e) {
        this(_code, _msg);
        exception = e;
    }


    @Override
    public String toString() {
        String fmt = "{ type: %d, code: %d, msg : %s}";
        return String.format(fmt, type, code, msg);
    }

    //from http://arnab.ch/blog/2013/08/asynchronous-http-requests-in-android-using-volley/

    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

}
