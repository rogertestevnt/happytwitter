package sample.study.happytwitter.bootcamp.utils

object ValidatorUtils {

  /** 1 - soma customizada
   *
   * Requisitos
   * 1. Deve somar o numero 'a' com número 'b'
   * 2. Os 2 números devem ser somados como números positivos (mesmo que tenham sido enviados negavitos)
   */
  fun customSUM(a: Int, b: Int): Int {
    val positiveA: Int = if (a < 0) {
      a * -1
    } else {
      a
    }

    // TODO criar testes, verificar que existem cenários de falha, e corrigir o método para se enquadrar nos requisitos definidos

    return positiveA + b
  }

  /** 2 - Validador de nome completo
   *
   * Requisitos
   * 1. Nome não pode ser vazio ou nulo
   * 2. Nome deve conter pelo menos um espaço separando conjunto de letras
   * 3. Nome deve conter pelo menos duas vogais
   * 4. O Tamanho do nome deve ser menor do que 100 caracteres no seu total
   */
  fun validateFullName(name: String?): Boolean {
    // TODO implementar a lógica conforme for definindo testes
    return false
  }

  /** 3 - Validador de número esquisito
   *
   * Requisitos
   * 1. Número deve ser positivo
   * 2. Número deve ser divisivel por 2 (resto 0)
   * 3. Número deve ser divisivel por 3 (resto 0)
   * 4. Número deve ser menor que 1337
   */
  fun validateWeirdNumber(number: Int): Boolean {
    // TODO implementar a lógica conforme for definindo testes
    return false
  }

  /** 4 - Crie seu validador de email
   *
   * TODO Requisitos
   */
  fun validateEmail(email: String): Boolean {
    // TODO Implementar
    return false
  }

  interface FunnyCallback {
    fun actionOne()
    fun actionTwo(param: Int)
  }

  /** 5 - use a chamada correta
   *
   * Requisitos
   * 1. se um dos 2 valores for negativo, nenhum método deve ser chamado
   * 2. se a soma dos 2 valores for maior que 10, o método "actionTwo", caso contrário chamar "actionOne"
   * 3. o valor passado no método "actionTwo" deve ser o resultado de uma multiplicação entre os 2 valores
   */
  fun callCorrectFunction(value1: Int, value2: Int, callback: FunnyCallback) {
    // TODO implementar
  }
}