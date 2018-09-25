package sample.study.happytwitter.bootcamp.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidatorUtilsTest {

  @Test
  fun customSUM1PositiveNumbers() {
    assertEquals(3, ValidatorUtils.customSUM(1, 2))
    assertEquals(5, ValidatorUtils.customSUM(3, 2))
    assertEquals(6, ValidatorUtils.customSUM(3, 3))
    assertEquals(110, ValidatorUtils.customSUM(100, 10))
  }

  @Test
  fun `customSUM - A negative number`() {
    assertEquals(3, ValidatorUtils.customSUM(-1, 2))
    assertEquals(4, ValidatorUtils.customSUM(-2, 2))
    assertEquals(5, ValidatorUtils.customSUM(-3, 2))
    assertEquals(102, ValidatorUtils.customSUM(-100, 2))
  }

  @Test
  fun `customSUM - B negative number`() {
    // TODO implementar validação
  }

  @Test
  fun `customSUM - AB negative numbers`() {
    // TODO implementar validação
  }

  // TODO create other tests
}