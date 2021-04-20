package com.example.yaxin.webprofile;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/** Tests related to interacting with {@link DrawerLayout}  */
@RunWith (AndroidJUnit4.class)
public class mainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Test that clicking on a Navigation Drawer Item Login will open the correct fragment.
     * Espresso: openDrawer, onView, withText, perform, click, matches, check, isDisplayed
     */
   @Test
    public void testALoginClick() {
        openDrawer(R.id.drawer_layout);
        onView(withText("Login")).perform(click());
        onView(allOf(withId(R.id.Oauth), withText("Waiting for authorization...."))).check(matches(isDisplayed()));
        onView(withId(R.id.Login)).perform(click());
        onView(allOf(withId(R.id.Oauth), withText("Waiting for Authorization...\nAuthroization Success!"))).check(matches(isDisplayed()));
    }

    //test profile if all things loading right
     @Test
    public void testBProfile(){
        openDrawer(R.id.drawer_layout);
        onView(withText("Profile")).perform(click());
        onView(allOf(withId(R.id.profile_github), withText("https://github.com/yaxtang"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.profile_name), withText("yaxtang"))).check(matches(isDisplayed()));
      }

    /**
     * Test following and follow together to see if it workds
     */
    @Test
    public void testCFollowing() {
        openDrawer(R.id.drawer_layout);
        onView(withText("Following")).perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_following))).atPosition(0)
                .onChildView(allOf(withId(R.id.name),withText("hwu63"))).check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_following))).atPosition(0)
                .onChildView(withId(R.id.unfollow_button))
                .perform(click());
    }
        @Test
    public void testDFollowers() {
        openDrawer(R.id.drawer_layout);
        onView(withText("Following")).perform(click());
        openDrawer(R.id.drawer_layout);
        onView(withText("Followers")).perform(click());

        // onView(withId(R.id.avatar)).check(matches(isClickable()));
        onView(allOf(withId(R.id.name), withText("hwu63"))).check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_follow))).atPosition(2)
                .onChildView(allOf(withId(R.id.name), withText("hwu63")))
                .check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_follow))).atPosition(0)
                .onChildView(allOf(withId(R.id.name), withText("xycFromUIUC")))
                .check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_follow))).atPosition(0)
                .onChildView(allOf(withId(R.id.avatar)))
                .check(matches(isClickable()));
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_follow))).atPosition(2)
                .onChildView(allOf(withId(R.id.follow_button)))
                .perform(click());
        openDrawer(R.id.drawer_layout);
        onView(withText("Following")).perform(click());
        onView(allOf(withId(R.id.name), withText("hwu63"))).check(matches(isDisplayed()));
    }

    @Test
    public void testEPublic() {
        openDrawer(R.id.drawer_layout);
        onView(withText("Public Repositories")).perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list))).atPosition(0)
                .onChildView(allOf(withId(R.id.icon)))
                .perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list))).atPosition(3)
                .onChildView(allOf(withId(R.id.icon)))
                .perform(click());
    }
    @Test
    public void testFNotification() {
        openDrawer(R.id.drawer_layout);
        onView(withText("Public Repositories")).perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list))).atPosition(0)
                .onChildView(allOf(withId(R.id.icon)))
                .perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list))).atPosition(3)
                .onChildView(allOf(withId(R.id.icon)))
                .perform(click());
    }
    @Test
    public void testGFollowi() {
        openDrawer(R.id.drawer_layout);
        onView(withText("Following")).perform(click());
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_following))).atPosition(0)
                .onChildView(allOf(withId(R.id.name),withText("hwu63"))).check(matches(isDisplayed()));
        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_following))).atPosition(0)
                .onChildView(withId(R.id.unfollow_button))
                .perform(click());
    }
}
