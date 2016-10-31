package com.mikemunhall.simpletwitterstats.model

import scala.collection.mutable

class TwitterTimeSeriesData {

  val tweets = new BasicCounterRollingTimeSeriesMetrics("tweets", () => 0l)
  val tweetsWithEmojis = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithEmojis", () => 0l)
  val tweetsWithUrls = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithUrls", () => 0l)
  val tweetsWithPhotoUrls = new ListLenCounterRollingTimeSeriesMetrics("tweetsWithPhotoUrls", () => 0l)
  val emojis = new OccurrenceRollingTimeSeriesMetrics("emojis", () => mutable.Map[String, Long]())
  val hashtags = new OccurrenceRollingTimeSeriesMetrics("hashtags", () => mutable.Map[String, Long]())
  val domains = new OccurrenceRollingTimeSeriesMetrics("domains", () => mutable.Map[String, Long]())

}