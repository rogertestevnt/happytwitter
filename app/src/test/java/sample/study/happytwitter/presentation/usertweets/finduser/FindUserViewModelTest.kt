package sample.study.happytwitter.presentation.usertweets.finduser

import com.nhaarman.mockito_kotlin.any
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import sample.study.happytwitter.base.network.NetworkingViewState
import sample.study.happytwitter.data.twitter.ITwitterRepo
import sample.study.happytwitter.data.twitter.TwitterUser
import sample.study.happytwitter.utils.schedulers.ISchedulerProvider
import sample.study.happytwitter.utils.schedulers.MockSchedulerProvider

@RunWith(MockitoJUnitRunner::class)
class FindUserViewModelTest {

  @Mock
  private lateinit var twitterRepository: ITwitterRepo

  private lateinit var schedulerProvider: ISchedulerProvider

  private lateinit var viewModel: FindUserViewModel

  private lateinit var testObserver: TestObserver<FindUserViewState>

  private val lastPosition
    get() = testObserver.values().lastIndex

  private val validUser = TwitterUser(1, "valid user", "valid", "valid description", "", "", "")

  @Before
  fun before() {
    schedulerProvider = MockSchedulerProvider()

    viewModel = FindUserViewModel(twitterRepository, schedulerProvider)

    testObserver = viewModel.states()
        .test()
  }

  @Test
  fun `Initial ViewState`() {
    testObserver.assertValueAt(lastPosition, FindUserViewState(null, false, NetworkingViewState.Idle))
  }

  @Test
  fun `SearchUserAction - Verify Searching will emit`() {
    `when`(twitterRepository.getUser(any())).thenReturn(Observable.just(validUser))

    viewModel.actions(Observable.just(FindUserAction.SearchUserAction("username")))

    testObserver.assertValueAt(lastPosition - 1) { viewState -> viewState.searchUserNetworking == NetworkingViewState.Loading }
    testObserver.assertValueAt(lastPosition) { viewState -> viewState.searchUserNetworking != NetworkingViewState.Loading }
  }

  /**
   * 1 - Verificar Ação SearchUserAction com um retorno valido
   */

  /**
   * 2 - Verificar Ação SearchUserAction com usuário não encontrado
   *
   * val response = Response.error<Void>(404, ResponseBody.create(MediaType.parse(""), "{\"errors\": [{\"code\":50, \"message\": \"User not found.\"}]}"))
   * `when`(twitterRepository.getUser(any())).thenReturn(Observable.error(HttpException(response)))
   */

  /**
   * 3 - Verificar Ação SearchUserAction usuário desabilitado
   *
   * val response = Response.error<Void>(402, ResponseBody.create(MediaType.parse(""), "{\"errors\": [{\"code\":63, \"message\": \"User not found.\"}]}"))
   * `when`(twitterRepository.getUser(any())).thenReturn(Observable.error(HttpException(response)))
   */

  /**
   * 4 - Verificar ChangeUserAction campo não vazio
   */

  /**
   * 5 - Verificar ChangeuserAction campo vazio
   */
}