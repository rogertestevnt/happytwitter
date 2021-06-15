package sample.study.happytwitter.instrumented.twitersearch

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import sample.study.happytwitter.R
import sample.study.happytwitter.instrumented.GenericTestClass
import sample.study.happytwitter.instrumented.TAG
import sample.study.happytwitter.utils.CommonTestFunctions
import sample.study.happytwitter.utils.CommonTestFunctions.Companion.assertElementIsDisplayed
import sample.study.happytwitter.utils.CommonTestFunctions.Companion.pressBack


@LargeTest
@RunWith(AndroidJUnit4::class)
class TwitterSearchInstrumented : GenericTestClass() {

    companion object {
        private const val invalidTwitterName = "abc"
        private const val disabledTwitterName = "disabled"
    }

    @Test
    fun verifyTwitterSearchView() {
        assertElementIsDisplayed(R.id.username_edittext)
        assertElementIsDisplayed(R.id.search_user_button)
        assertElementIsDisplayed(R.id.imageView)
    }

    @Test
    fun verifyInvalidTwitterNameMsg() {
        typeUser(invalidTwitterName)
        assertElementIsDisplayed(context.getString(R.string.usertweets_finduser_error_user_not_found))
    }

    @Test
    fun verifyDisabledTwitterNameMsg() {
        typeUser(disabledTwitterName)
        assertElementIsDisplayed(context.getString(R.string.usertweets_finduser_error_user_disabled))
    }

    @Test
    fun verifyValidTwitterUser() {
        typeUser(validTwitterName)
        assertElementIsDisplayed(R.id.profile_picture_imageview)
        pressBack()
    }

    @Test
    fun verifyResponseForAllValidPublicUsers() {
        val users = jsonFunctions.jsonContents
        users.forEach  {
            typeUser(it.screen_name)
            assertElementIsDisplayed(R.id.profile_picture_imageview)
            pressBack()
        }
    }

    private fun typeUser(twitterUser:String){
        try{
            CommonTestFunctions.typeText(R.id.username_edittext, twitterUser)
        }catch(e:Exception){
            Log.e(TAG, e.toString())
            fileName = CommonTestFunctions.getFileName(testName.methodName)
            screenshotHelper.saveBitmap(screenshotHelper.takeScreenshot(), context, fileName)
            Assert.fail()
        }
    }


}