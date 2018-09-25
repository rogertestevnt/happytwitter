package sample.study.happytwitter.bootcamp.presenter

class MvpPresenter : MvpContract.Presenter {

  private val loginService: LoginService = LoginService()

  private val view: MvpContract.View = MvpActivity()

  override fun newUsernameValue(username: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun newPasswordValue(password: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun login(username: String, password: String) {
    // TODO implement
  }

  override fun forgotPassword() {
    view.displayForgotPasswordScreen()
  }
}