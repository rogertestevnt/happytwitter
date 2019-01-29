package sample.study.happytwitter.instrumented.tweetslist

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.core.AllOf
import org.junit.Test
import org.junit.runner.RunWith
import sample.study.happytwitter.R
import sample.study.happytwitter.instrumented.GenericTestClass
import sample.study.happytwitter.utils.CommonTestFunctions
import sample.study.happytwitter.utils.DrawableMatchers
import sample.study.happytwitter.utils.RecyclerViewMatcher


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
        CommonTestFunctions.assertElementIsDisplayed("JOYFUL to meet you at DevTests!")
    }

    @Test
    fun testVerifyImageViewIsDisplayed(){
        CommonTestFunctions.typeText(R.id.username_edittext, validTwitterName)
        onView(ViewMatchers.withId(R.id.tweets_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(POSITION, click()))
        onView(AllOf.allOf(withId(R.id.tweet_sentiment_imageview),
                isDescendantOfA(RecyclerViewMatcher(R.id.tweets_recyclerview).atPosition(POSITION))))
                .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testVerifyHappyIconIsDisplayed(){
        CommonTestFunctions.typeText(R.id.username_edittext, validTwitterName)
        scrollListToPositionAndPerformClick(POSITION)
        assertSentimentIconIsDisplayed(R.drawable.sentiment_happy_icon, POSITION)
    }

    @Test
    fun testVerifySadIconIsDisplayed(){
        CommonTestFunctions.typeText(R.id.username_edittext, validTwitterName)
        scrollListToPositionAndPerformClick(POSITION - 4)
        assertSentimentIconIsDisplayed(R.drawable.sentiment_sad_icon, POSITION - 4)
    }

    @Test
    fun testVerifyNeutralIconIsDisplayed(){
        CommonTestFunctions.typeText(R.id.username_edittext, validTwitterName)
        scrollListToPositionAndPerformClick(0)
        assertSentimentIconIsDisplayed(R.drawable.sentiment_neutral_icon, 0)
    }

    private fun scrollListToPositionAndPerformClick(position: Int){
        onView(ViewMatchers.withId(R.id.tweets_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    private fun assertSentimentIconIsDisplayed(drawable:Int, position:Int){
        onView(AllOf.allOf(withId(R.id.tweet_sentiment_imageview),
                isDescendantOfA(RecyclerViewMatcher(R.id.tweets_recyclerview).atPosition(position))))
                .check(ViewAssertions.matches(DrawableMatchers.withDrawable(drawable)))
    }
}

