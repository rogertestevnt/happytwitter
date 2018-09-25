package sample.study.happytwitter.bootcamp.presenter

class LoginService {
  /**
   * isso deveria ser uma ação feita em background.
   *
   * O Retorno por callback ou via Stream (Observable)
   */
  fun performLogin(username: String, password: String): Boolean {
    if (username == "usuario" && password == "senhaSecreta") {
      return true
    }
    return false
  }
}