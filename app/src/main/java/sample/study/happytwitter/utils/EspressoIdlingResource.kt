package sample.study.happytwitter.utils

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "ASYNC_CALLS"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

}