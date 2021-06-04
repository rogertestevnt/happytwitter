package sample.study.happytwitter.uiautomator

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.filters.RequiresDevice
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import android.util.Log
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import java.lang.Thread.sleep


private const val APP_PACKAGE = "sample.study.happytwitter.mock"
private const val PHONE_PACKAGE = "com.android.phone"
private const val LAUNCH_TIMEOUT = 5000L
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
        ViewMatchers.assertThat(launcherPackage, CoreMatchers.notNullValue())
        mDevice.wait(
                Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT
        )

        // Launch the app
        context = InstrumentationRegistry.getContext()
        intent = context.packageManager.getLaunchIntentForPackage(APP_PACKAGE).apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    @After
    fun afterRun(){
        //TODO: to be implemented
    }

    @Test
    fun verifyTurnWiFiOffAndLaunchApp(){

        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val network = wifiManager.connectionInfo
        if (wifiManager.isWifiEnabled){
            val networkName = network.ssid
            Log.v(TAG, "connected to WiFi SSID:" + " " + networkName.toString())
            try{
                wifiManager.disconnect()
                wifiManager.disableNetwork(network.networkId)
                Log.v(TAG, "Wi-Fi disconnected")
            }catch (e:Exception){
                Log.e(TAG, testName.methodName + ":" +
                        "Wi-Fi disconnected throws error:" + e.message)
            }
        }

        launchApp()

        try{
            wifiManager.enableNetwork(network.networkId, true)
            wifiManager.reconnect()
            Log.v(TAG, "Wi-Fi reconnected")
        }catch (e:Exception){
            Log.e(TAG, testName.methodName + ":" +
                    "Wi-Fi reconnected throws error:" + e.message)
        }
    }

    @Test
    fun verifyMinimizeAppAndLaunchFromRecentApps(){
        launchApp()
        mDevice.pressHome()
        mDevice.pressRecentApps()
        sleep(1000)
        val app = mDevice.findObject(By.text("MockHTW"))
        app.click()
        sleep(1000)
        val userNameField = mDevice.findObject(By.res(
                APP_PACKAGE, "username_edittext"))
        assert(userNameField.isClickable)
    }

    @Ignore
    @Test
    fun verifyLaunchPhonePackageFromCallButton(){
        /**
         * Test result may fail depending on the Android customization
         * found in the device under test
         * Use "adb shell pm list packages -f phone" to get the phone package name
         */
        launchApp()
        val appCallButton = mDevice.findObject(By.res(
                APP_PACKAGE, "call_button"))
        try{
            appCallButton.click()
        }catch (e:Exception){
            Log.e(TAG, testName.methodName + ":" + e.message)
        }

        sleep(2000)
        val phoneCallButton = mDevice.findObject(By.res(PHONE_PACKAGE, "call_button"))
        assert(phoneCallButton.isClickable)
    }

    private fun launchApp(){
        context.startActivity(intent)

        // Wait for the app to appear
        mDevice.wait(
                Until.hasObject(By.pkg(APP_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT
        )

        sleep(3000)
    }

}