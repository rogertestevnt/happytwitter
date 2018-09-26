package sample.study.happytwitter.instrumented.tweetslist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Test
import org.junit.runner.RunWith
import sample.study.happytwitter.R
import sample.study.happytwitter.instrumented.GenericTestClass
import sample.study.happytwitter.utils.CommonTestFunctions


@LargeTest
@RunWith(AndroidJUnit4::class)
class TweetsListInstrumented:GenericTestClass() {

    companion object {
        private const val POSITION = 6
    }

    @Test
    fun verifyScrollToItemByPositionAndVerifyText() {
        CommonTestFunctions.typeText(R.id.username_edittext, validTwitterName)
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.tweets_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(POSITION, click()))
        CommonTestFunctions.assertElementIsDisplayed("JOYFUL to meet you at Venturus!")
    }
}