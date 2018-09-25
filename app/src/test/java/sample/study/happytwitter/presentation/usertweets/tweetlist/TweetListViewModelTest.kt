package sample.study.happytwitter.presentation.usertweets.tweetlist

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import sample.study.happytwitter.base.network.NetworkingViewState
import sample.study.happytwitter.base.network.NetworkingViewState.Error
import sample.study.happytwitter.data.google.AnalyzedTweet
import sample.study.happytwitter.data.google.IGoogleRepo
import sample.study.happytwitter.data.twitter.ITwitterRepo
import sample.study.happytwitter.data.twitter.TwitterTweet
import sample.study.happytwitter.presentation.usertweets.tweetlist.TweetListViewState.Companion.ERROR_PRIVATE_USER
import sample.study.happytwitter.presentation.usertweets.tweetlist.tweetitem.AnalyzeTweetRequestState
import sample.study.happytwitter.presentation.usertweets.tweetlist.tweetitem.TweetItemState
import sample.study.happytwitter.presentation.usertweets.tweetlist.tweetitem.TweetSentiment.HAPPY
import sample.study.happytwitter.presentation.usertweets.tweetlist.tweetitem.TweetSentiment.NEUTRAL
import sample.study.happytwitter.utils.schedulers.ISchedulerProvider
import sample.study.happytwitter.utils.schedulers.MockSchedulerProvider

@RunWith(MockitoJUnitRunner::class)
class TweetListViewModelTest {

  @Mock
  private lateinit var twitterRepository: ITwitterRepo

  @Mock
  private lateinit var googleRepository: IGoogleRepo

  private lateinit var schedulerProvider: ISchedulerProvider

  private lateinit var viewModel: TweetListViewModel

  private lateinit var testObserver: TestObserver<TweetListViewState>

  private val lastPosition
    get() = testObserver.values().lastIndex

  @Before
  fun before() {
    schedulerProvider = MockSchedulerProvider()

    viewModel = TweetListViewModel(twitterRepository, googleRepository, schedulerProvider)

    testObserver = viewModel.states()
        .test()
  }

  @Test
  fun `Initial ViewState`() {
    testObserver.assertValueAt(lastPosition, TweetListViewState(NetworkingViewState.Idle, null))
  }

  @Test
  fun `LoadTweetsAction - valid screenName`() {
    val screenName = "username"
    val twitter1 = TwitterTweet(1, "1234", "message", screenName)
    val twitter2 = TwitterTweet(2, "1234", "message", screenName)
    val twitter3 = TwitterTweet(3, "1234", "message", screenName)
    val twitterList = arrayListOf(twitter1, twitter2, twitter3)
    val tweetsItemList: ArrayList<TweetItemState> = arrayListOf()
    twitterList.forEach { tweetsItemList.add(TweetItemState(it)) }

    Mockito.`when`(twitterRepository.getTweetsByUser(screenName))
        .thenReturn(Observable.just(twitterList))

    viewModel.actions(Observable.just(TweetListAction.LoadTweetsAction(screenName)))

    testObserver.assertValueAt(lastPosition - 1) { viewState -> viewState.loadTweetsNetworking == NetworkingViewState.Loading }
    testObserver.assertValueAt(lastPosition, TweetListViewState(NetworkingViewState.Success, tweetsItemList))
  }

  @Test
  fun `LoadTweetsAction - Private screenName`() {
    val privateName = "private"
    val response = Response.error<Void>(401, ResponseBody.create(MediaType.parse(""), ""))

    Mockito.`when`(twitterRepository.getTweetsByUser(privateName))
        .thenReturn(Observable.error(HttpException(response)))

    viewModel.actions(Observable.just(TweetListAction.LoadTweetsAction(privateName)))

    testObserver.assertValueAt(lastPosition - 1) { viewState -> viewState.loadTweetsNetworking == NetworkingViewState.Loading }
    testObserver.assertValueAt(lastPosition) { viewState -> (viewState.loadTweetsNetworking as Error).errorMessage == ERROR_PRIVATE_USER }
  }

  @Test
  fun `LoadTweetsAction - Unknown Error`() {
    val screenName = "username"
    val response = Response.error<Void>(999, ResponseBody.create(MediaType.parse(""), "ERROR"))

    Mockito.`when`(twitterRepository.getTweetsByUser(screenName))
        .thenReturn(Observable.error(HttpException(response)))

    viewModel.actions(Observable.just(TweetListAction.LoadTweetsAction(screenName)))

    testObserver.assertValueAt(lastPosition - 1) { viewState -> viewState.loadTweetsNetworking == NetworkingViewState.Loading }
    testObserver.assertValueAt(lastPosition) { viewState -> viewState.loadTweetsNetworking is NetworkingViewState.Error }
  }

  @Test
  fun `AnalyzeTweetAction - test 1`() {
    val screenName = "username"
    val tweetId = 1L
    val twitter1 = TwitterTweet(1, "1234", "message", screenName)
    val twitter2 = TwitterTweet(2, "1234", "message", screenName)
    val twitter3 = TwitterTweet(3, "1234", "message", screenName)
    val twitterList = arrayListOf(twitter1, twitter2, twitter3)
    val tweetsItemList: ArrayList<TweetItemState> = arrayListOf()
    twitterList.forEach { tweetsItemList.add(TweetItemState(it)) }
    val tweetItemState = TweetItemState(twitter1, NEUTRAL, null)

    Mockito.`when`(twitterRepository.getTweetsByUser(screenName))
        .thenReturn(Observable.just(twitterList))
    Mockito.`when`(googleRepository.analyzeSentiment(twitter1))
        .thenReturn(Single.just(AnalyzedTweet(tweetId, HAPPY)))


    viewModel.actions(Observable.merge(Observable.just(TweetListAction.LoadTweetsAction(screenName)),
      Observable.just(TweetListAction.AnalyzeTweetAction(tweetItemState))))

    testObserver.assertValueAt(lastPosition - 1) { viewState -> viewState.loadedTweetList?.find { it.tweet.id == tweetId }?.analyzeSentimentRequestState == AnalyzeTweetRequestState.Loading }
    testObserver.assertValueAt(lastPosition) { viewState -> viewState.loadedTweetList?.find { it.tweet.id == tweetId }?.analyzeSentimentRequestState == AnalyzeTweetRequestState.Success }
    testObserver.assertValueAt(lastPosition) { viewState -> viewState.loadedTweetList?.find { it.tweet.id == tweetId }?.sentiment == HAPPY }
  }
}