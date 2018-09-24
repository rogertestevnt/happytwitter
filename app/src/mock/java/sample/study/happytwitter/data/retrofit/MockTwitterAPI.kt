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

    if(screenName == "private"){
      return Single.create {
        Timer().schedule(delay = 5000) {
        it.onError(HttpException(Response.error<TweetListResult>(HttpURLConnection.HTTP_UNAUTHORIZED,
                ResponseBody.create(MediaType.parse(""),""))))
      }
    }
    }

//    //Read the tweets list from file tweets.json
//    val tweets = jsonFunctions.jsonTweetsListContents.filter { it.screen_name?.toLowerCase() == screenName.toLowerCase() }
//    return Single.just(tweets).delay(5, TimeUnit.SECONDS)



    val happyTweet = TwitterTweet(1, "00/09/2002", "Happy message", screenName)
    val happyTweet1 = TwitterTweet(2, "00/09/2002", "Happy message one", screenName)
    val happyTweet2 = TwitterTweet(3, "00/09/2002", "Happy message two", screenName)
    val sadTweet = TwitterTweet(4, "00/09/2002", "SAD message", screenName)
    val sadTweet1 = TwitterTweet(5, "00/09/2002", "SAD message one", screenName)
    val sadTweet2 = TwitterTweet(6, "00/09/2002", "SAD message two", screenName)
    val neutralTweet = TwitterTweet(7, "00/09/2002", "neutral message", screenName)
    val neutralTweet1 = TwitterTweet(8, "00/09/2002", "neutral message one", screenName)
    val neutralTweet2 = TwitterTweet(9, "00/09/2002", "neutral message two", screenName)
    return Single.just(listOf(happyTweet, happyTweet1, happyTweet2, sadTweet, sadTweet1, sadTweet2, neutralTweet, neutralTweet1, neutralTweet2))
        .delay(2, SECONDS)

    //Return included just to clear a build error
//    return Single.create {
//      Timer().schedule(delay = 3000) {
//        it.onError(HttpException(Response.error<TweetListResult>(HttpURLConnection.HTTP_BAD_REQUEST,
//                ResponseBody.create(MediaType.parse(""),""))))
//      }
//    }
  }
}