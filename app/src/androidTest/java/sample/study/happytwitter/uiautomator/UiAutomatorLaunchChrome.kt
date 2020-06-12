package sample.study.happytwitter.uiautomator

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.filters.RequiresDevice
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val CHROME_PACKAGE = "com.android.chrome"
private const val LAUNCH_TIMEOUT = 5000L

@LargeTest
@RequiresDevice
class UiAutomatorLaunchChrome {

    private lateinit var mDevice: UiDevice
    private lateinit var context: Context
    private lateinit var intent: Intent

    @Before
    fun beforeRun() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        mDevice.pressHome()

        // Wait for launcher
        val launcherPackage: String = mDevice.launcherPackageName
        ViewMatchers.assertThat(launcherPackage, CoreMatchers.notNullValue())
        mDevice.wait(
                Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT
        )
        context = InstrumentationRegistry.getContext()
        intent = context.packageManager.getLaunchIntentForPackage(CHROME_PACKAGE).apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    @After
    fun afterRun(){
        mDevice.pressHome()
    }

    @Test
    fun launchChrome() {
        mDevice.pressHome()
        context.startActivity(intent)
        mDevice.wait(Until.hasObject(By.pkg(CHROME_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
        mDevice.findObject(By.res(CHROME_PACKAGE,"terms_accept")).click()
        Thread.sleep(1000)
        mDevice.findObject(By.text("Next")).click()
        Thread.sleep(1000)
        mDevice.findObject(By.text("No thanks")).click()
    }

}

