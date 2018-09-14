package sample.study.happytwitter.data.retrofit

import com.google.gson.Gson
import epson.com.br.rewards.androidapp.utils.JsonFunctions
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import sample.study.happytwitter.data.twitter.TwitterTweet
import sample.study.happytwitter.data.twitter.TwitterUser
import sample.study.happytwitter.data.twitter.remote.TwitterAPI
import sample.study.happytwitter.data.twitter.remote.TwitterError
import sample.study.happytwitter.presentation.usertweets.tweetlist.TweetListResult
import java.net.HttpURLConnection
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MockTwitterAPI @Inject constructor(private val jsonFunctions: JsonFunctions) : TwitterAPI {

  override fun getUser(screenName: String): Single<TwitterUser> {
    if(screenName == "disabled"){
      val disabledError = TwitterError.TwitterErrorList(listOf(TwitterError.TwitterErrorItem(63, null)))
      val errorBody = Gson().toJson(disabledError)

      return Single.create {
        Timer().schedule(delay = 3000){
          it.onError(HttpException(Response.error<TwitterUser>(HttpURLConnection.HTTP_BAD_REQUEST,
                  ResponseBody.create(MediaType.parse(""), errorBody))))
        }
      }
    }

    val user = jsonFunctions.jsonContents.find { it.screen_name.toLowerCase() == screenName.toLowerCase() }
    if(user != null) {
      return Single.just(user).delay(5, TimeUnit.SECONDS)
    }

    val notFoundError = TwitterError.TwitterErrorList(listOf(TwitterError.TwitterErrorItem(50, "not found")))
    val errorBody = Gson().toJson(notFoundError)

    return Single.create {
      Timer().schedule(delay = 3000) {
        it.onError(HttpException(Response.error<TwitterUser>(HttpURLConnection.HTTP_BAD_REQUEST,
                ResponseBody.create(MediaType.parse(""), errorBody))))
      }
    }
  }

  override fun getTweetsByUser(screenName: String): Single<List<TwitterTweet>> {

    //TODO: to handle 401 error message - private account

    if (screenName == "private") {
      val privateError = TweetListResult.LoadTweetsResult.PrivateList
      val errorBody = Gson().toJson(privateError)

      return Single.create {
        Timer().schedule(delay = 3000) {
          it.onError(HttpException(Response.error<TweetListResult>(HttpURLConnection.HTTP_BAD_REQUEST,
                  ResponseBody.create(MediaType.parse(""), errorBody))))
        }
      }
    }


    //Read the tweets list from file twwets.json
    var tweets = jsonFunctions.jsonTweetsListContents.find { it.screen_name?.toLowerCase() == screenName.toLowerCase() }

    if(tweets != null){
      return Single.just(listOf(tweets)).delay(5, SECONDS)
    }




//    val happyTweet = TwitterTweet(1, "00/09/2002", "Happy message", screenName)
//    val sadTweet = TwitterTweet(2, "00/09/2002", "SAD message", screenName)
//    val neutralTweet = TwitterTweet(3, "00/09/2002", "neutral message", screenName)
//    return Single.just(listOf(happyTweet, sadTweet, neutralTweet))
//        .delay(2, SECONDS)

    //Return included just to clear a build error
    return Single.create {
      Timer().schedule(delay = 3000) {
        it.onError(HttpException(Response.error<TweetListResult>(HttpURLConnection.HTTP_BAD_REQUEST,
                ResponseBody.create(MediaType.parse(""),""))))
      }
    }
  }
}