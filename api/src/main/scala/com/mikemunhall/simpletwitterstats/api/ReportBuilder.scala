package com.mikemunhall.simpletwitterstats.api

import com.mikemunhall.simpletwitterstats.timeSeriesData
import com.mikemunhall.simpletwitterstats.model.metrics.RollingTimeSeriesMetrics
import com.mikemunhall.simpletwitterstats.model.reports.{Header, Occurrence, Report}
import java.time.temporal.ChronoUnit._
import java.util.concurrent.ConcurrentHashMap
import collection.JavaConverters._
import scala.collection.immutable.ListMap

// TODO: This is a wreck. Please refactor.
object ReportBuilder {

  def build: Report = {
    val startTimestamp = timeSeriesData.startDateTime.toString
    val endTimestamp = timeSeriesData.endDateTime.toString

    val secondsBetween = SECONDS.between(timeSeriesData.startDateTime, timeSeriesData.endDateTime)
    val minutesBetween = MINUTES.between(timeSeriesData.startDateTime, timeSeriesData.endDateTime)
    val hoursBetween = HOURS.between(timeSeriesData.startDateTime, timeSeriesData.endDateTime)

    // TODO: This can be more accurate by using Double instead of Int
    // TODO: Parallelize all dis...
    val totalTweets = calculateTotalTweets
    val tweetsPerSecond = if (secondsBetween != 0) totalTweets / secondsBetween else 0
    val tweetsPerMinute = tweetsPerSecond * 60
    val tweetsPerHour = tweetsPerMinute * 24

    val allHashTags = calculateOccurrenceItem(timeSeriesData.hashtags.values)
    val allDomains = calculateOccurrenceItem(timeSeriesData.domains.values)
    val allEmojis = calculateOccurrenceItem(timeSeriesData.emojis.values)

    val topHashTags = ListMap(allHashTags.toSeq.sortWith(_._2 > _._2):_*).take(10).keys.toList
    val topDomains = ListMap(allDomains.toSeq.sortWith(_._2 > _._2):_*).take(10).keys.toList
    val topEmojis = ListMap(allEmojis.toSeq.sortWith(_._2 > _._2):_*).take(10).keys.toList

    // TODO: Generalize/externalize the filter values
    val allPhotoDomains = ListMap(allDomains.toSeq.sortWith(_._2 > _._2):_*).filter(d => d._1.contains("pic.twitter") || d._1.contains("instagram"))
    val topPhotoDomains = allPhotoDomains.take(10).keys.toList

    val tweetsWithHashtags = calculateCounterItem(timeSeriesData.tweetsWithHashtags.values)
    val percentWithHashtags = (BigDecimal(tweetsWithHashtags.toDouble / totalTweets.toDouble) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    val tweetsWithUrls = calculateCounterItem(timeSeriesData.tweetsWithUrls.values)
    val percentWithUrls = (BigDecimal(tweetsWithUrls.toDouble / totalTweets.toDouble) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    val tweetsWithPhotoUrls = calculateCounterItem(timeSeriesData.tweetsWithPhotoUrls.values)
    val percentWithPhotoUrls = (BigDecimal(tweetsWithPhotoUrls.toDouble / totalTweets.toDouble) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    val tweetsWithEmojis = calculateCounterItem(timeSeriesData.tweetsWithEmojis.values)
    val percentWithEmojis = (BigDecimal(tweetsWithEmojis.toDouble / totalTweets.toDouble) * 100).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

    Report(
      Header(startTimestamp, endTimestamp),
      totalTweets,
      tweetsPerSecond,
      tweetsPerMinute,
      tweetsPerHour,
      Occurrence(topEmojis, percentWithEmojis),
      Occurrence(topHashTags, percentWithHashtags),
      Occurrence(topDomains, percentWithUrls),
      Occurrence(topPhotoDomains, percentWithPhotoUrls))
  }

  private def calculateCounterItem(items: RollingTimeSeriesMetrics.Values[Long]): Long = {
    val ht = for {
      h <- items.values
      m <- h.values
      s <- m.values().asScala
    } yield s

    ht.sum
  }

  private def calculateOccurrenceItem(items: RollingTimeSeriesMetrics.Values[ConcurrentHashMap[String, Long]]) = {
    val ht = for {
      h <- items.values
      m <- h.values
      s <- m.values().asScala
    } yield s

    ht.filter(item => item.size != 0).map(m => m.asScala).flatten.foldLeft(Map[String, Long]() withDefaultValue 0L) { (acc, _val) => acc + (_val._1 -> (acc(_val._1) + _val._2)) }
  }

  private def calculateTotalTweets: Long = {
    val tweets = for {
      h <- timeSeriesData.tweets.values.values
      m <- h.values
      s <- m.values().asScala
    } yield s

    tweets.sum
  }

}
