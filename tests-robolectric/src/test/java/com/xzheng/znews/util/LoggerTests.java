package com.xzheng.znews.util;

import com.xzheng.znews.RobolectricGradleTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by xzheng on 10/9/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class LoggerTests {
    private Logger _logger;
    private static final String tag = "some tag";

    @Before
    public void setUp() {
        _logger = new Logger(tag);

    }

    @After
    public void tearDown() {
        _logger = null;
    }

    @Test
    public void testBuilder() {
        _logger = new Logger.Builder().tag(tag).build();
        assertEquals("wrong tag", _logger._tag, tag);
    }

}
