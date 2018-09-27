package sample.study.happytwitter.integration

import android.support.test.espresso.intent.rule.IntentsTestRule
import org.junit.Rule
import sample.study.happytwitter.presentation.splash.SplashActivity

@Rule
var intentsTestRule = IntentsTestRule<SplashActivity>(SplashActivity::class.java)

class IntegrationTestsSample {

  /**
   * Implementar um teste que verifica se o package PHONE
   * é chamado ao click do botão da tela principal
   *
   * Usar "adb shell pm list packages -f phone" para pegar o package de phone
   */
}