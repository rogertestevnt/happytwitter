package sample.study.happytwitter.utils

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import com.jraska.falcon.FalconSpoonRule
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf
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

        fun assertElementIsDisplayed(elementId: Int,recyclerViewId: Int, position: Int){
            onView(AllOf.allOf(
                ViewMatchers.withId(elementId),
                ViewMatchers.isDescendantOfA(
                    RecyclerViewMatcher(recyclerViewId).atPosition(
                        position
                    )
                )
            )).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        fun assertElementIsDisplayedOnView(elementId: Int) {
            onView(AllOf.allOf(ViewMatchers.withId(elementId), ViewMatchers.isCompletelyDisplayed()))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        fun assertElementIsDisplayedOnView(text: String) {
            onView(AllOf.allOf(ViewMatchers.withText(text), ViewMatchers.isCompletelyDisplayed()))
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

        fun clickElementOnView(elementId: Int, elementPosition: Int) {
            onView(ViewMatchers.withId(elementId))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    elementPosition,
                    ViewActions.click()
                ))
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

        fun getCurrentActivity(): Activity? {
            val currentActivity = arrayOf<Activity?>(null)
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED)
                val it: Iterator<Activity> = resumedActivity.iterator()
                currentActivity[0] = it.next()
            }
            return currentActivity[0]
        }

        fun screenShot(rule: FalconSpoonRule, tag: String) {
            rule.screenshot(getCurrentActivity(), tag)
            Log.i("asd", "Screenshot taken: $tag")
        }
    }
}