package com.xzheng.znews.util;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xzheng on 10/9/14.
 * Hello world.
 */
public class Logger {
    protected static final String DEFAULT_TAG = "Logger";
    protected String _tag = DEFAULT_TAG;

    public static class Builder {
        private String _tag;
        public Builder() {}
        public Builder tag(String tag) {
            _tag = tag;
            return this;
        }

        public Logger build() {
            return new Logger(_tag);
        }
    }


    protected Logger(String tag) {
        if(tag != null) {
            _tag = tag;
        }
    }


    public void e(Throwable throwable, String format, Object... args) {
        Log.e(_tag, createFormattedString(format, args), throwable);
    }

    public void e(Throwable throwable) {
        Log.e(_tag, "Exception:", throwable);
    }

    public void e(String format, Object... args) {
        Log.e(_tag, createFormattedString(format, args));
    }

    public void i(String format, Object... args) {
        Log.i(_tag, createFormattedString(format, args));
    }

    public void d(String format, Object... args) {
        Log.d(_tag, createFormattedString(format, args));
    }

    public void v(String format, Object... args) {
        Log.v(_tag, createFormattedString(format, args));
    }




    protected String createFormattedString(String format, Object... args) {
        String formattedString;
        try {
            formattedString = String.format(format, args);
        } catch(Exception e) {
            formattedString = e.toString();
        }
        return formattedString;

    }
}
