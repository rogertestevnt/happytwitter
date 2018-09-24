package sample.study.happytwitter.instrumented.results

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import sample.study.happytwitter.R
import sample.study.happytwitter.instrumented.GenericTestClass
import sample.study.happytwitter.utils.CommonTestFunctions

@LargeTest
@RunWith(AndroidJUnit4::class)
class TweetsResultInstrumented:GenericTestClass() {

    @Test
    fun verifyPrivateTweetAccountResponse(){
        CommonTestFunctions.typeText(R.id.username_edittext, "private")
        CommonTestFunctions.assertElementIsDisplayed(context.getString(R.string.tweetlist_secured_tweets))
    }
}