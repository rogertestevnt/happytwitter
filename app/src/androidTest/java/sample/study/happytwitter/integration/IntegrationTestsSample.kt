package sample.study.happytwitter.integration

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.toPackage
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.filters.RequiresDevice
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import sample.study.happytwitter.R
import sample.study.happytwitter.presentation.splash.SplashActivity


@Rule
var intentsTestRule = IntentsTestRule<SplashActivity>(SplashActivity::class.java)

const val PHONE_PACKAGE = "com.android.phone"


class IntegrationTestsSample {

    @Ignore
    @MediumTest
    @RequiresDevice
    @Test
    fun validateIntentSentToPhonePackage() {

        intentsTestRule.launchActivity(Intent())

        // User action that results in an external "phone" activity being launched.
        onView(withId(R.id.call_button)).perform(click())
        // Using a canned RecordedIntentMatcher to validate that an intent resolving
        // to the "phone" activity has been sent.
        intended(toPackage(PHONE_PACKAGE))

        Intents.release()
    }
}