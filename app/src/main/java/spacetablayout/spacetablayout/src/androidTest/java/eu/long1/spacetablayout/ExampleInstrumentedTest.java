package spacetablayout.spacetablayout.src.androidTest.java.eu.long1.spacetablayout;

import static org.junit.Assert.assertEquals;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Instrumentation InstrumentationRegistry;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("eu.long1.spacetablayout.test", appContext.getPackageName());
    }
}
