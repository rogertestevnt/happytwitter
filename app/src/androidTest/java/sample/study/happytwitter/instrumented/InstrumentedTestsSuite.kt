package sample.study.happytwitter.instrumented

import org.junit.runner.RunWith
import org.junit.runners.Suite
import sample.study.happytwitter.instrumented.results.TweetsResultInstrumented
import sample.study.happytwitter.instrumented.twitersearch.TwitterSearchInstrumented
import sample.study.happytwitter.uiautomator.UiAutomatorSystemTests

@RunWith(Suite::class)
@Suite.SuiteClasses(
        TweetsResultInstrumented::class,
        TwitterSearchInstrumented::class,
        UiAutomatorSystemTests::class
)

class InstrumentedTestsSuite