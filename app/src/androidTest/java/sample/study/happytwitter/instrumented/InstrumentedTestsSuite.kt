package sample.study.happytwitter.instrumented

import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.Suite
import sample.study.happytwitter.instrumented.tweetslist.TweetsListInstrumented
import sample.study.happytwitter.instrumented.twitersearch.TwitterSearchInstrumented
import sample.study.happytwitter.uiautomator.UiAutomatorSystemTests

@RunWith(Suite::class)
@Ignore("To be used inside as part of continuous integration")
@Suite.SuiteClasses(
        TweetsListInstrumented::class,
        TwitterSearchInstrumented::class,
        UiAutomatorSystemTests::class
)

class InstrumentedTestsSuite