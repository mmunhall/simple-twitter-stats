package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object TwitterTimeSeriesData {
  def apply() = new TwitterTimeSeriesData()
}

/**
  * Contains all the metrics being tracked.
  */
class TwitterTimeSeriesData {

  var startTimestamp = LocalDateTime.now
  var endTimestamp = LocalDateTime.now

  val tweets = new BasicCounterRollingTimeSeriesMetrics("tweets", () => 0l)
  val tweetsWithEmojis = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithEmojis", () => 0l)
  val tweetsWithUrls = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithUrls", () => 0l)
  val tweetsWithPhotoUrls = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithPhotoUrls", () => 0l)
  val emojis = new OccurrenceRollingTimeSeriesMetrics("emojis", () => mutable.Map[String, Long]())
  val hashtags = new OccurrenceRollingTimeSeriesMetrics("hashtags", () => mutable.Map[String, Long]())
  val domains = new OccurrenceRollingTimeSeriesMetrics("domains", () => mutable.Map[String, Long]())

  def add(tweet: Tweet) = {
    val f1 = Future {
      if (tweet.timestamp.isBefore(startTimestamp)) startTimestamp = tweet.timestamp // TODO: This is not accurate. It will be incorrect when the 24-hour period rolls
      if (tweet.timestamp.isAfter(endTimestamp)) endTimestamp = tweet.timestamp
    }
    val f2 = Future { tweets.add(tweet.timestamp, true) }
    val f3 = Future { tweetsWithEmojis.add(tweet.timestamp, tweet.emojis) }
    val f4 = Future { tweetsWithUrls.add(tweet.timestamp, tweet.domains) }
    val f5 = Future { tweetsWithPhotoUrls.add(tweet.timestamp, tweet.photoDomains) }
    val f6 = Future { emojis.add(tweet.timestamp, tweet.emojis) }
    val f7 = Future { hashtags.add(tweet.timestamp, tweet.hashtags) }
    val f8 = Future { domains.add(tweet.timestamp, tweet.domains) }

    // Parallelize the update to each metric, but await results. This is necessary (for now) because the time series maps are not thread safe,
    // not to mention that to keep stats accurate we we have to ensure each metric is updated for a particular tweet before moving on to the
    // next tweet.
    for {
      f1Result <- f1
      f2Result <- f2
      f3Result <- f3
      f4Result <- f4
      f5Result <- f5
      f6Result <- f6
      f7Result <- f7
      f8Result <- f8
    } yield Unit // TODO: Expect failures and deal with them properly
  }

}