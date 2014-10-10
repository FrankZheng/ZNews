package com.xzheng.znews.util;

import android.util.Log;

import com.xzheng.znews.RobolectricGradleTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * Created by xzheng on 10/9/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(shadows = {LoggerTests.MyShadowLog.class})
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

    @Test
    public void testLogError() {

        Throwable mockThrowable = mock(Throwable.class);
        when(mockThrowable.toString()).thenReturn("I am mock");
        _logger.e(mockThrowable, "This is a error, [%d]", 1001);

        String expectedLog = String.format("[%s]This is a error, [1001] - I am mock", tag);
        assertEquals("wrong error log", MyShadowLog.errorLog, expectedLog);

    }

    @Implements(Log.class)
    public static class MyShadowLog {
        public static String errorLog;
        public static int e(String tag, String msg, Throwable throwable) {
            errorLog = String.format("[%s]%s - %s", tag, msg, throwable.toString());
            return 0;
        }
    }

}
