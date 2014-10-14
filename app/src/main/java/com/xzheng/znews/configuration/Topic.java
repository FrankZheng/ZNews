package com.xzheng.znews.configuration;

/**
 * Created by xzheng on 10/14/14.
 */
public enum Topic {
    Top(""),
    Tech("t"),
    Finance("b"),
    Entertainment("e"),
    Sports("s");

    private final String _topic;
    private Topic(final String topic) {
        _topic = topic;
    }
    @Override
    public String toString() {
        return _topic;
    }
}
