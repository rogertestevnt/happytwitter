package sample.study.happytwitter.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.DrawableRes
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Suppress
 * Use this annotation on test classes or test methods that should not be included
 * in a test suite. If the annotation appears on the class then no tests in that
 * class will be included. If the annotation appears only on a test method then only
 * that method will be excluded.
 */

@Suppress("unused")

class CommonTestFunctions internal constructor() {

    /**
     * Test Functions to be used in the test class
     */

    companion object {
        fun typeText(fieldId: Int, textToType: String) {
            onView(ViewMatchers.withId(fieldId))
                    .perform(ViewActions.typeText(textToType), ViewActions.pressImeActionButton())
        }

        fun typeText(fieldText: String, textToType: String) {
            onView(ViewMatchers.withText(fieldText))
                    .perform(ViewActions.typeText(textToType), ViewActions.pressImeActionButton())
        }

        fun assertElementIsDisplayed(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        fun assertElementIsDisplayed(text: String) {
            onView(ViewMatchers.withText(text))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        fun assertElementIsDisplayedOnView(elementId: Int) {
            Espresso.onView(AllOf.allOf(ViewMatchers.withId(elementId), ViewMatchers.isCompletelyDisplayed()))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        fun assertElementIsDisplayedOnView(text: String) {
            Espresso.onView(AllOf.allOf(ViewMatchers.withText(text), ViewMatchers.isCompletelyDisplayed()))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        fun assertElementIsNotDisplayed(text: String) {
            onView(ViewMatchers.withText(text))
                    .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        }

        fun assertElementIsNotDisplayed(elementId: Int) {
            onView(ViewMatchers.withText(elementId))
                    .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        }

        fun assertElementIsEnabled(text: String) {
            onView(ViewMatchers.withText(text))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
        }

        fun assertElementIsEnabled(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
        }

        fun clickElement(text: String) {
            onView(ViewMatchers.withText(text))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                    .perform(ViewActions.click())
        }

        fun clickElement(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                    .perform(ViewActions.click())
        }

        fun pressIMEButton() {
            ViewActions.pressImeActionButton()
        }

        fun closeSoftKeyboard() {
            Espresso.closeSoftKeyboard()
        }

        fun clearText(elementId: Int) {
            this.clickElement(elementId)
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                    .perform(ViewActions.clearText())
            this.closeSoftKeyboard()
        }

        fun replaceText(elementId: Int, newText: String) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                    .perform(ViewActions.replaceText(newText))
            this.closeSoftKeyboard()
        }

        fun clickElementOnView(elementId: Int) {
            onView(AllOf.allOf(ViewMatchers.withId(elementId), ViewMatchers.isCompletelyDisplayed()))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                    .perform(ViewActions.click())

        }

        fun clickElementOnView(text: String) {
            onView(AllOf.allOf(ViewMatchers.withText(text), ViewMatchers.isCompletelyDisplayed()))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                    .perform(ViewActions.click())
        }


        fun swipeRight(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                    .perform(ViewActions.swipeRight())
        }

        fun swipeLeft(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
                    .perform(ViewActions.swipeLeft())
        }

        fun assertElementIsNotEnabled(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))
        }

        fun assertElementIsNotEnabled(text: String) {
            onView(ViewMatchers.withText(text))
                    .check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))
        }

        fun assertElementMatchesText(elementId: Int, elementText: String) {
            onView(ViewMatchers.withId(elementId))
                    .check(ViewAssertions.matches(ViewMatchers.withText(elementText)))
        }

        fun isElementDisplayed(elementId: Int): Boolean {
            try {
                onView(ViewMatchers.withId(elementId))
                        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            } catch (e: NoMatchingViewException) {
                return false
            }
            return true
        }

        fun scrollToElement(elementId: Int) {
            onView(ViewMatchers.withId(elementId))
                    .perform(ViewActions.scrollTo())
        }

        fun scrollToElement(text: String) {
            onView(ViewMatchers.withText(text))
                    .perform(ViewActions.scrollTo())
        }

        fun pressBack() {
            Espresso.pressBack()
        }

        fun getText(matcher: Matcher<View>): String {
            var stringHolder = ""
            onView(matcher).perform(object : ViewAction {
                override fun getDescription(): String {
                    return ""
                }

                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(TextView::class.java)
                }

                override fun perform(uiController: UiController, view: View) {
                    val tv = view as TextView
                    stringHolder = tv.text.toString()
                }
            })
            return stringHolder
        }

        fun getFileName(testName: String): String {
            val date = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            return testName + "_" + date
        }

        fun drawableIsCorrect(@DrawableRes drawableResId: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("with drawable from resource id: ")
                    description.appendValue(drawableResId)
                }

                override fun matchesSafely(target: View?): Boolean {
                    if (target !is ImageView) {
                        return false
                    }
                    if (drawableResId < 0) {
                        return target.drawable == null
                    }
                    val expectedDrawable = ContextCompat.getDrawable(target.context, drawableResId)
                            ?: return false

                    val bitmap = (target.drawable as BitmapDrawable).bitmap
                    val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
                    return bitmap.sameAs(otherBitmap)
                }
            }
        }

    }
}