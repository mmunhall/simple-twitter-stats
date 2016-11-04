package com.mikemunhall.simpletwitterstats.api

import com.mikemunhall.simpletwitterstats.timeSeriesData
import com.mikemunhall.simpletwitterstats.model.metrics.RollingTimeSeriesMetrics
import com.mikemunhall.simpletwitterstats.model.reports.{Header, Occurrence, Report}
import java.time.temporal.ChronoUnit._

import scala.collection.immutable.ListMap

object ReportBuilder {

  def build: Report = {
    val startTimestamp = timeSeriesData.startTimestamp.toString
    val endTimestamp = timeSeriesData.endTimestamp.toString

    val secondsBetween = SECONDS.between(timeSeriesData.startTimestamp, timeSeriesData.endTimestamp)
    val minutesBetween = MINUTES.between(timeSeriesData.startTimestamp, timeSeriesData.endTimestamp)
    val hoursBetween = HOURS.between(timeSeriesData.startTimestamp, timeSeriesData.endTimestamp)

    // TODO: This can be more accurate by using doubles and just multiplying
    // TODO: Parallelize these...
    val totalTweets = calculateTotalTweets
    val tweetsPerSecond = if (secondsBetween != 0) totalTweets / secondsBetween else 0
    val tweetsPerMinute = if (minutesBetween != 0) totalTweets / minutesBetween else 0
    val tweetsPerHour = if (hoursBetween != 0) totalTweets / hoursBetween else 0

    val allHashTags = calculateOccurrenceItem(timeSeriesData.hashtags.values)
    val topHashTags = ListMap(allHashTags.toSeq.sortWith(_._2 > _._2):_*).take(10).keys.toList

    val allEmojis = calculateOccurrenceItem(timeSeriesData.emojis.values)
    val topEmojis = ListMap(allEmojis.toSeq.sortWith(_._2 > _._2):_*).take(10).keys.toList

    val allDomains = calculateOccurrenceItem(timeSeriesData.domains.values)
    val topDomains = ListMap(allDomains.toSeq.sortWith(_._2 > _._2):_*).take(10).keys.toList

    // TODO: Generalize/externalize the filter values
    val topPhotoDomains = ListMap(allDomains.toSeq.sortWith(_._2 > _._2):_*).filter(d => d._1.contains("pic.twitter") || d._1.contains("instagram")).take(10).keys.toList

    Report(Header(startTimestamp, endTimestamp), totalTweets, tweetsPerSecond, tweetsPerMinute, tweetsPerHour, Occurrence(topEmojis, 0), Occurrence(topHashTags, 0), Occurrence(topDomains, 0), Occurrence(topPhotoDomains, 0))
  }

  private def calculateOccurrenceItem(items: RollingTimeSeriesMetrics.Values[scala.collection.mutable.Map[String, Long]]) = {
    val ht = for {
      h <- items.values
      m <- h.values
      s <- m.values
    } yield s

    ht.filter(m => m.size != 0).flatten.foldLeft(Map[String, Long]() withDefaultValue 0L) { (acc, m) => acc + (m._1 -> (acc(m._1) + m._2)) }
  }

  private def calculateTotalTweets: Long = {
    val tweets = for {
      h <- timeSeriesData.tweets.values.values
      m <- h.values
      s <- m.values
    } yield s

    tweets.sum
  }

}
