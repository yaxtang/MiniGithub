package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 11/1/2017.
 */


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HelloWorldEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void listGoesOverTheFold() {
// This doesn't.
       // onView(withId(R.id.nav_view))
             //   .perform(actionOnItemAtPosition(2, click()));
    }
}


