package sample.study.happytwitter.instrumented.twitersearch

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import epson.com.br.rewards.androidapp.utils.JsonFunctions
import kotlinx.android.synthetic.main.usertweets_finduser_view.view.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import sample.study.happytwitter.R
import sample.study.happytwitter.instrumented.GenericTestClass
import sample.study.happytwitter.utils.CommonTestFunctions
import java.lang.Thread.sleep


@LargeTest
@RunWith(AndroidJUnit4::class)
class TwitterSearchInstrumented : GenericTestClass() {

    private var validTwitterName: String = ""
    private var invalidTwitterName: String = "abc"
    private val disabledTwitterName: String = "disabled"

    @Before
    override fun beforeRun() {
        super.beforeRun()
        validTwitterName = jsonFunctions.jsonContents.first().screen_name }

    @Test
    fun verifyTwitterSearchView() {
        CommonTestFunctions.assertElementIsDisplayed(R.id.username_edittext)
        CommonTestFunctions.assertElementIsDisplayed(R.id.search_user_button)
        CommonTestFunctions.assertElementIsDisplayed(R.id.imageView)
    }

    @Test
    fun verifyInvalidTwitterNameMsg() {
        typeUser(invalidTwitterName)
        CommonTestFunctions.assertElementIsDisplayed(context.getString(R.string.usertweets_finduser_error_user_not_found))
    }

    @Test
    fun verifyDisabledTwitterNameMsg() {
        typeUser(disabledTwitterName)
        CommonTestFunctions.assertElementIsDisplayed(context.getString(R.string.usertweets_finduser_error_user_disabled))
    }

    @Test
    fun verifyValidTwitterUser() {
        typeUser(validTwitterName)
        sleep(3000)
        CommonTestFunctions.assertElementIsDisplayed(R.id.profile_picture_imageview)
        CommonTestFunctions.pressBack()
    }

    @Test
    fun verifyResponseForAllValidPublicUsers() {
        val users = jsonFunctions.jsonContents
        users.forEach  {
            typeUser(it.screen_name)
            sleep(3000)
            CommonTestFunctions.assertElementIsDisplayed(R.id.profile_picture_imageview)
            CommonTestFunctions.pressBack()
        }
    }

    private fun typeUser(twitterUser:String){
        CommonTestFunctions.typeText(R.id.username_edittext, twitterUser)
    }


}