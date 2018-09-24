package sample.study.happytwitter.instrumented

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.IdlingRegistry
import android.support.test.rule.ActivityTestRule
import android.util.Log
import com.google.gson.Gson
import epson.com.br.rewards.androidapp.utils.JsonFunctions
import sample.study.happytwitter.App
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import sample.study.happytwitter.presentation.splash.SplashActivity
import sample.study.happytwitter.utils.EspressoIdlingResource

open class GenericTestClass {

    lateinit var context: App
    lateinit var jsonFunctions: JsonFunctions

    @get:Rule
    val activityRule = ActivityTestRule(SplashActivity::class.java, false,
            false)

    @get:Rule
    var testName = TestName()

    @Before
    open fun beforeRun() {
        context = InstrumentationRegistry.getTargetContext().applicationContext as App
        activityRule.launchActivity(Intent(context, SplashActivity::class.java))
        jsonFunctions = JsonFunctions(context)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        Log.v("Idling Resource", "Resource registered in test class!")
    }

    @After
    open fun afterRun() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
        Log.v("Idling Resource", "Resource unregistered in test class!")
        activityRule.finishActivity()
    }
}