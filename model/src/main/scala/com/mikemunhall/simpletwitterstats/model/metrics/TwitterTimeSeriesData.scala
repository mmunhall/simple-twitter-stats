package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime

import scala.collection.mutable

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
    if (tweet.timestamp.isBefore(startTimestamp))
      startTimestamp = tweet.timestamp

    if (tweet.timestamp.isAfter(endTimestamp))
      endTimestamp =  tweet.timestamp

    tweets.add(tweet.timestamp, true)
    tweetsWithEmojis.add(tweet.timestamp, tweet.emojis)
    tweetsWithUrls.add(tweet.timestamp, tweet.domains)
    tweetsWithPhotoUrls.add(tweet.timestamp, tweet.photoDomains)
    emojis.add(tweet.timestamp, tweet.emojis)
    hashtags.add(tweet.timestamp, tweet.hashtags)
    domains.add(tweet.timestamp, tweet.domains)
  }

}