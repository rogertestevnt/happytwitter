package sample.study.happytwitter.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.support.test.InstrumentationRegistry
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ScreenshotHelper {

    private lateinit var rootView: View
    private var currentActivity: Activity? = null
    private val tagName = "ScreenshotHelper"


    fun takeScreenshot(): Bitmap {

        currentActivity = getCurrentActivity()
        if (currentActivity != null) {
            rootView = currentActivity!!.findViewById<View>(android.R.id.content).rootView
        }

        rootView.isDrawingCacheEnabled = true
        return rootView.drawingCache
    }

    fun saveBitmap(bitmap: Bitmap, context: Context, fileName: String) {
        if (Environment.getExternalStorageState() != "mounted") {
            Log.e(tagName, "sdcard not mounted")
        } else {
            val imagePath = File(context.getExternalFilesDir(null),
                    "/$fileName.png")
            val fos: FileOutputStream
            try {
                fos = FileOutputStream(imagePath)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                Log.e(tagName, e.message, e)
            } catch (e: Exception) {
                Log.e(tagName, e.message, e)
            }
        }
    }

    private fun getCurrentActivity(): Activity? {
        val currentActivity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val allActivities = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED)
            if (!allActivities.isEmpty()) {
                currentActivity[0] = allActivities.iterator().next()
            }
        }
        return currentActivity[0]
    }
}