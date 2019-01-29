package sample.study.happytwitter.presentation.usertweets.finduser

import sample.study.happytwitter.base.mvi.IViewState
import sample.study.happytwitter.base.network.NetworkingViewState
import sample.study.happytwitter.data.twitter.TwitterUser

data class FindUserViewState(
    val twitterUser: TwitterUser? = null,
    val isUserNameValid: Boolean = false,
    val searchUserNetworking: NetworkingViewState = NetworkingViewState.Idle
) : IViewState {
  val isSearchEnabled: Boolean
    get() = isUserNameValid && searchUserNetworking != NetworkingViewState.Loading

  companion object {
    const val ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val ERROR_USER_DISABLED = "ERROR_USER_DISABLED"
    const val ERROR_OVER_CAPACITY = "ERROR_OVER_CAPACITY"
    const val ERROR_NOT_AUTHORIZED = "ERROR_NOT_AUTHORIZED"
    const val ERROR_USER_LOCKED = "ERROR_USER_LOCKED"
  }
}