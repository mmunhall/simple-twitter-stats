package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime
import scala.collection.mutable

case class Tweet(timestamp: LocalDateTime, emojis: List[String], hashtags: List[String], urls: List[String])

abstract class RollingTimeSeriesMetrics[T](val label: String, default: () => T, val add: Tweet => Unit) {

  type Values[T] = mutable.Map[Int, mutable.Map[Int, mutable.Map[Int, T]]]

  // TODO: Only the seconds map should be mutable. All other maps can be immutable.
  var values: Values[T] = {
    val hMap = mutable.Map[Int, mutable.Map[Int, mutable.Map[Int, T]]]()
    (0 to 23).foreach(h => hMap(h) = {
      val mMap = mutable.Map[Int, mutable.Map[Int, T]]()
      (0 to 59).foreach(m => mMap(m) = {
        val sMap = mutable.Map[Int, T]()
        (0 to 59).foreach(s => sMap(s) = default())
        sMap
      })
      mMap
    })
    hMap
  }
}

class CounterRollingTimeSeriesMetrics[Long](label: String, default: () => Long, add: Tweet => Unit) extends RollingTimeSeriesMetrics(label, default, add)
class OccurrenceRollingTimeSeriesMetrics[`mutable.Map[String, Long]`](label: String, default: () => mutable.Map[String, Long], add: Tweet => Unit) extends RollingTimeSeriesMetrics(label, default, add)

class TwitterTimeSeriesData {

  val tweetTotals = new CounterRollingTimeSeriesMetrics[Int]("totals", () => 0, (t: Tweet) => {
    println(t)
  })
}