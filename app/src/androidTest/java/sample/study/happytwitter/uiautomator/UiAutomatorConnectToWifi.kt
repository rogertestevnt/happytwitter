package sample.study.happytwitter.uiautomator

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.ACTION_PICK_WIFI_NETWORK
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.filters.LargeTest
import android.support.test.filters.RequiresDevice
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Thread.sleep


private const val APP_PACKAGE = "com.android.settings"
private const val LAUNCH_TIMEOUT = 10000L
private const val TAG = "UiAutomator"


@RunWith(AndroidJUnit4::class)
@LargeTest
@RequiresDevice
class UiAutomatorSystemTests {

    private lateinit var mDevice: UiDevice
    private lateinit var context: Context
    private lateinit var intent: Intent

    @get:Rule
    var testName = TestName()

    @Before
    fun beforeRun() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        mDevice.pressHome()

        // Wait for launcher
        val launcherPackage: String = mDevice.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        mDevice.wait(
                Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT
        )
        context = InstrumentationRegistry.getContext()
        intent = context.packageManager.getLaunchIntentForPackage(APP_PACKAGE).apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    @After
    fun afterRun(){
        mDevice.pressHome()
    }

    @Test
    fun verifyTurnWiFiOnAndConnect(){

        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }

        assertThat(wifiManager.connectionInfo.supplicantState.name, `is` (equalTo("DISCONNECTED")))

        sleep (3000)
        context.startActivity(Intent(ACTION_PICK_WIFI_NETWORK))
        sleep(8000)
        val fileContents = readFile()
        mDevice.findObject(By.text(fileContents[0])).click()
        sleep (5000)

        if (mDevice.findObject(By.focused(true)) != null) {
            mDevice.findObject(By.focused(true))
                    .setText(fileContents[1])
        }

        if (mDevice.findObject(By.text("Connect")) != null) {
            mDevice.findObject(By.text("Connect")).click()
        }

        if (mDevice.findObject(By.text("CONNECT")) != null) {
            mDevice.findObject(By.text("CONNECT")).click()
        }

        //Verify if it is connected
        sleep(3000)
        assertThat(wifiManager.connectionInfo.supplicantState.name, `is` (equalTo("COMPLETED")))

    }

    private fun readFile(): Array<String> {
        val file = File("/data/local/tmp/wifiInfo.txt")
        val content = arrayOf("","")

        if (file.exists()) {

            val br = BufferedReader(FileReader(file))
            var i = 0
            var line: String?=null
            while (br.readLine().also { line = it } != null) {
                content[i] = line.toString().trim()
                i++
            }
        }

        return  content

    }

}
