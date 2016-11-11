package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

import scala.collection.mutable

object RollingTimeSeriesMetrics {
  type Values[T] = mutable.Map[Int, mutable.Map[Int, ConcurrentHashMap[Int, T]]]
}

/**
  * A collection of 24-hour rolling metrics at second resolution.
  *
  * @param label
  * @param default
  * @tparam T
  */
abstract class RollingTimeSeriesMetrics[T](val label: String, default: () => T) {

  // TODO: Only the seconds map should be mutable. All other maps can be immutable.
  var values: RollingTimeSeriesMetrics.Values[T] = {
    val hMap = mutable.Map[Int, mutable.Map[Int, ConcurrentHashMap[Int, T]]]()
    (0 to 23).foreach(h => hMap(h) = {
      val mMap = mutable.Map[Int, ConcurrentHashMap[Int, T]]()
      (0 to 59).foreach(m => mMap(m) = {
        val sMap = new ConcurrentHashMap[Int, T]()
        (0 to 59).foreach(s => sMap.put(s, default()))
        sMap
      })
      mMap
    })
    hMap
  }

  def add(timestamp: LocalDateTime, obj: Any): Unit
}
