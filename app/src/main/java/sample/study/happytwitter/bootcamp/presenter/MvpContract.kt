package sample.study.happytwitter.bootcamp.presenter

interface MvpContract {
  interface View {
    fun showLoading()
    fun hideLoading()
    fun showLoginError()
    fun hideLoginError()

    fun markUserNameAsValid()
    fun markUserNameAsInvalid()

    fun markPassword(valid: Boolean)

    fun displayMainScreen()
    fun displayForgotPasswordScreen()
  }

  interface Presenter {
    fun newUsernameValue(username: String)
    fun newPasswordValue(password: String)
    fun login(username: String, password: String)
    fun forgotPassword()
  }
}