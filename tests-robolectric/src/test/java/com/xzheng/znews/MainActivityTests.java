package com.xzheng.znews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTests extends BaseTestCase {

    private MainActivity _activity;

    @Before
    public void setUp() {
        _activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertNotNull(_activity);
    }

    @After
    public void tearDown() {
        _activity = null;
    }

    @Test
    public void test() {
        verify(getApplication().mockLibraryModel).update();
        
    }


}
