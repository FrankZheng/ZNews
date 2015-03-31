package com.xzheng.znews.net;

/**
 * Created by zhengxiaoqiang on 15/3/10.
 */
public class MyResponse {
    public Object data;

    @Override
    public String toString() {
        return String.format("data:%s",
                data == null ? "null" : data.toString());
    }
}
