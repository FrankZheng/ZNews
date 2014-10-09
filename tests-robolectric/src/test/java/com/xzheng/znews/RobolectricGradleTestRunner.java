package com.xzheng.znews;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;
import org.robolectric.res.FsFile;

/**
 * Created by xzheng on 10/5/14.
 */
public class RobolectricGradleTestRunner extends RobolectricTestRunner {
    private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 18;

    public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    /*
        gradle put source files under src/main/java, which is different with ADT.
        So robolectric could NOT load the library res / manifest correctly.
        Here we put the project.properties under :app/src/main, with is at the same level with res folder.
        So that robolectric could load the library properties file correctly.
        And also do following things to make it load the library res / mainifest files correctly.
        An better way to override the findLibraries() to load the project.properties in this module.
     */
    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String manifestProperty = "../app/src/main/AndroidManifest.xml";
        String resProperty = "../app/src/main/res";
        return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty)) {
            @Override
            public int getTargetSdkVersion() {
                return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
            }
            @Override
            protected AndroidManifest createLibraryAndroidManifest(FsFile libraryBaseDir) {
                FsFile mainDir = libraryBaseDir.join("src/main");
                return new AndroidManifest(mainDir.join("AndroidManifest.xml"), mainDir.join("res"));
            }
        };
    }


}
