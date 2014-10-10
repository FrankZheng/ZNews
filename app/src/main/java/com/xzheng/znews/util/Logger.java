package com.xzheng.znews.util;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xzheng on 10/9/14.
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
        _tag = tag;
    }


    public void e(Throwable throwable, String format, Object... args) {
        Log.e(_tag, createFormattedString(format, args), throwable);
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
