package sample.study.happytwitter.data.retrofit

import io.reactivex.Single
import sample.study.happytwitter.data.google.remote.GoogleAPI
import sample.study.happytwitter.data.google.remote.GoogleAnalyzeBody
import sample.study.happytwitter.data.google.remote.GoogleAnalyzeResponse
import sample.study.happytwitter.data.google.remote.GoogleAnalyzeResponse.DocumentSentiment
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

class MockGoogleAPI @Inject constructor() : GoogleAPI {
  override fun analyzeSentiment(body: GoogleAnalyzeBody): Single<GoogleAnalyzeResponse> {
    val text = body.document.content
    val response = when {
        text.contains("HAPPY", true) -> GoogleAnalyzeResponse(DocumentSentiment(0.8))
        text.contains("JOYFUL", true) -> GoogleAnalyzeResponse(DocumentSentiment(0.8))
        text.contains("SATISFIED", true) -> GoogleAnalyzeResponse(DocumentSentiment(0.8))
        text.contains("SAD", true) -> GoogleAnalyzeResponse(DocumentSentiment(-0.4))
        text.contains("BORED", true) -> GoogleAnalyzeResponse(DocumentSentiment(-0.4))
        text.contains("DEPRESSED", true) -> GoogleAnalyzeResponse(DocumentSentiment(-0.4))
        else -> GoogleAnalyzeResponse(DocumentSentiment(0.0))
    }
    return Single.just(response)
        .delay(4, SECONDS)
  }
}