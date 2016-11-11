package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object TwitterTimeSeriesData {
  def apply() = new TwitterTimeSeriesData()
}

/**
  * Contains all the metrics being tracked.
  */
class TwitterTimeSeriesData extends RollingDateTimeCalculations{

  var startDateTime = LocalDateTime.now
  var endDateTime = LocalDateTime.now

  val tweets = new BasicCounterRollingTimeSeriesMetrics("tweets", () => 0l)
  val tweetsWithEmojis = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithEmojis", () => 0l)
  val tweetsWithUrls = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithUrls", () => 0l)
  val tweetsWithHashtags = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithHashtags", () => 0l)
  val tweetsWithPhotoUrls = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithPhotoUrls", () => 0l)
  val emojis = new OccurrenceRollingTimeSeriesMetrics("emojis", () => new ConcurrentHashMap[String, Long]())
  val hashtags = new OccurrenceRollingTimeSeriesMetrics("hashtags", () => new ConcurrentHashMap[String, Long]())
  val domains = new OccurrenceRollingTimeSeriesMetrics("domains", () => new ConcurrentHashMap[String, Long]())

  def add(tweet: Tweet) = {
    val f1 = Future {
      // an accurate start date requires the end date to be set first to account for a possible 24-hour roll,
      // so set the end date first
      endDateTime = calculateNewEndDateTime(tweet.timestamp, endDateTime)
      startDateTime = calculateNewStartDateTime(tweet.timestamp, startDateTime, endDateTime)
    }
    val f2 = Future { tweets.add(tweet.timestamp, true) }
    val f3 = Future { tweetsWithEmojis.add(tweet.timestamp, tweet.emojis) }
    val f4 = Future { tweetsWithUrls.add(tweet.timestamp, tweet.domains) }
    val f5 = Future { tweetsWithHashtags.add(tweet.timestamp, tweet.hashtags )}
    val f6 = Future { tweetsWithPhotoUrls.add(tweet.timestamp, tweet.photoDomains) }
    val f7 = Future { emojis.add(tweet.timestamp, tweet.emojis) }
    val f8 = Future { hashtags.add(tweet.timestamp, tweet.hashtags) }
    val f9 = Future { domains.add(tweet.timestamp, tweet.domains) }

    // Parallelize the update to each metric, but await results. Awaiting is necessary (for now) because the time series maps are not thread safe,
    // not to mention that to keep stats accurate wgit e we have to ensure each metric is updated for a particular tweet before moving on to the
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
      f9Result <- f9
    } yield Unit // TODO: Expect exceptions and deal with them properly
  }

}