package com.supree.android.bakerapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.supree.android.bakerapp.views.RecipeCardsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewVideoTests {

    @Rule
    public ActivityTestRule<RecipeCardsActivity> mActivityRule = new ActivityTestRule<>(RecipeCardsActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void checkCardText_RecipeCardActivity() {

        onView(ViewMatchers.withId(R.id.fragment_container)).check(matches(isDisplayed()));
//        onView(ViewMatchers.withId(R.id.rvRecipe)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));

    }

    @Test
    public void checkVideoView_RecipeDetailActivity(){
        //onView(ViewMatchers.withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }

    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
