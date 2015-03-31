package com.xzheng.znews.net;

/**
 * Created by zhengxiaoqiang on 15/3/6.
 */
public interface IResponseListener {

    public void onResponse(MyResponse response);

    public void onErrorResponse(NetError error);

}
