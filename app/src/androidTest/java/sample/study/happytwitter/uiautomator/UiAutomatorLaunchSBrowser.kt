package sample.study.happytwitter.uiautomator

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import android.util.Log
import android.util.Log.INFO
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Thread.sleep


private const val SBROWSER_PACKAGE = "com.sec.android.app.sbrowser"
private const val LAUNCH_TIMEOUT = 10000L

class UiAutomatorLaunchSBrowser {

    private lateinit var mDevice: UiDevice
    private lateinit var context: Context
    private var intent: Intent? = null
    private var isAppInstalled: Boolean = false

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

        isAppInstalled = isAppInstalledOrNot(context, SBROWSER_PACKAGE)

        if (isAppInstalled) {
            intent = context.packageManager.getLaunchIntentForPackage(SBROWSER_PACKAGE).apply {
                // Clear out any previous instances
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        }
    }

    @After
    fun afterRun(){
        mDevice.pressHome()
    }

    @Test
    fun launchSBrowser() {
        if (isAppInstalled) {
            mDevice.pressHome()
            context.startActivity(intent)
            sleep(10000)

            if (mDevice.findObject(By.res(SBROWSER_PACKAGE,"help_intro_legal_agree_button")) != null){
                mDevice.findObject(By.res(SBROWSER_PACKAGE,"help_intro_legal_agree_button")).click()
            }

            if (mDevice.findObject(By.text("Remind me later")) != null) {
                mDevice.findObject(By.text("Remind me later")).click()
            }

            if (mDevice.findObject(By.text("REMIND ME LATER")) != null) {
                mDevice.findObject(By.text("REMIND ME LATER")).click()
            }

        }
    }

    private fun isAppInstalledOrNot(context: Context, applicationId: String): Boolean {
        try {
            context.packageManager.getPackageInfo(applicationId, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return false;
    }
}