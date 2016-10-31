package com.mikemunhall.simpletwitterstats.model

import scala.collection.mutable

class TwitterTimeSeriesData {

  val tweets = new BasicCounterRollingTimeSeriesMetrics[Long]("tweets", () => 0l)
  val tweetsWithEmojis = new ListLenCounterRollingTimeSeriesMetrics[Long]("tweetsWithEmojis", () => 0l)
  val tweetsWithUrls = new ListLenCounterRollingTimeSeriesMetrics[Long]("tweetsWithUrls", () => 0l)
  val tweetsWithPhotoUrls = new ListLenCounterRollingTimeSeriesMetrics[Long]("tweetsWithPhotoUrls", () => 0l)
  val emojis = new OccurrenceRollingTimeSeriesMetrics[Long]("emojis", () => mutable.Map[String, Long]())
  val hashtags = new OccurrenceRollingTimeSeriesMetrics[Long]("hashtags", () => mutable.Map[String, Long]())
  val domains = new OccurrenceRollingTimeSeriesMetrics[Long]("domains", () => mutable.Map[String, Long]())

}