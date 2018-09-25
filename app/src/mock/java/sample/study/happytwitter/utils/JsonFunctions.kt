package epson.com.br.rewards.androidapp.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sample.study.happytwitter.data.twitter.TwitterTweet
import sample.study.happytwitter.data.twitter.TwitterUser
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class JsonFunctions @Inject constructor(private val context: Context){

  private val gson = Gson()

  val jsonContents: List<TwitterUser>
    get() {

      val builder = readFile("twitter.json")

      return gson.fromJson(builder!!.toString(), object : TypeToken<List<TwitterUser>>() {}.type)
    }

  val jsonTweetsListContents: List<TwitterTweet>
    get() {

      val builder = readFile("tweets.json")

      return gson.fromJson(builder!!.toString(), object : TypeToken<List<TwitterTweet>>() {}.type)
    }

  private fun readFile(fileName:String):StringBuilder?{
    var inputStream: InputStream? = null
    var builder: StringBuilder? = null

    try {
      inputStream = context.assets.open(fileName)
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
    return builder
  }
}
