package com.xzheng.znews;

import org.robolectric.Robolectric;

/**
 * Created by xzheng on 10/5/14.
 */
public class BaseTestCase {
    public TestMainApplication getApplication() {
        return (TestMainApplication) Robolectric.application;
    }
}
