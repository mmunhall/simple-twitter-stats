package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime

import scala.collection.mutable

/**
  * A collection of 24-hour rolling metrics at second resolution.
  *
  * @param label
  * @param default
  * @tparam T
  */
abstract class RollingTimeSeriesMetrics[T](val label: String, default: () => T) {

  /* Values[T] is a nested map. The outer map represents a rolling 24-hours. The first inner map represents the minutes
     of each hour. The innermost map represents the seconds of each minute. The key of each map is an integer
     representing the time part. The value for each key is a map for hours and minutes, and the metric value T for
     seconds.

     e.g.:
       {
         "0": {       // hours
           "0": {     // minutes
             "0": 10, // seconds
             // ...
             "59": 2  // ... each second of the minute
           }          // ... for each minute of the hour
         },           // ... for each hour
       }
  */
  // TODO: These maps are NOT THREAD SAFE! Refactor use java.util.concurrent.ConcurrentHashMap.
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

  def add(timestamp: LocalDateTime, obj: Any): Unit
}
