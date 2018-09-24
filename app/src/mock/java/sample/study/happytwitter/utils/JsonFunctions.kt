package epson.com.br.rewards.androidapp.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sample.study.happytwitter.data.twitter.TwitterUser
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class JsonFunctions @Inject constructor(private val context: Context){

  private val gson = Gson()

  val jsonContents: List<TwitterUser>
    get() {

      var inputStream: InputStream? = null
      var builder: StringBuilder? = null

      try {
        inputStream = context.assets.open("twitter.json")
        builder = StringBuilder()
        inputStream!!.bufferedReader()
          .use { builder.append(it.readText()) }

      } catch (e: IOException) {
        Log.e("Json Function", e.message)
      } finally {
        try {
          inputStream?.close()
        } catch (e: IOException) {
          Log.e("Json Function", e.toString())
        }
      }

      return gson.fromJson(builder!!.toString(), object : TypeToken<List<TwitterUser>>() {}.type)
    }
}
