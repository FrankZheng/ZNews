package com.xzheng.znews;


import com.xzheng.znews.library.model.LibraryModel;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by xzheng on 10/5/14.
 */
public class TestMainApplication extends MainApplication implements TestLifecycleApplication {

    private ObjectGraph _injectionGraph = null;

    LibraryModel mockLibraryModel = null;




    @Module(
        includes = ApplicationModule.class,
        injects = {

        },
        overrides = true,
        library = true
    )
    public class TestModule {
        @Provides
        LibraryModel provideLibraryModel() {
            return mockLibraryModel;
        }
    }

    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {
        mockLibraryModel = mock(LibraryModel.class);

        _injectionGraph = ObjectGraph.create(new TestModule());

        _injectionGraph.injectStatics();

    }

    @Override
    public void afterTest(Method method) {

    }

    @Override
    public void inject(Object object) {
        _injectionGraph.inject(object);
    }
}
