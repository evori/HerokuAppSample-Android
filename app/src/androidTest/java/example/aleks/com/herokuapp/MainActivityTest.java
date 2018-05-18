package example.aleks.com.herokuapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.aleks.com.herokuapp.presentation.MainActivity;
import example.aleks.com.herokuapp.presentation.model.MovieItemModel;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by Aleksandar on 3.5.2018 г..
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule< MainActivity > mActivityRule = new ActivityTestRule<>( MainActivity.class );

    @Test
    public void typeQuery_Test() {

        final String searchString = "Action";

        onView(withId(R.id.searchView)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText(searchString), closeSoftKeyboard());

        // Check that the text was changed.
        onView(isAssignableFrom(EditText.class)).check(matches(withText(searchString)));
    }

    @Test
    public void validate_Query_Result() {

        final String searchString = "Dunkirk";

        onView(withId(R.id.searchView)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText(searchString), closeSoftKeyboard());

        onData( allOf( is( instanceOf( MovieItemModel.class ) ), hasEntry( equalTo( searchString ), is( searchString ) ) ) );

    }
}
