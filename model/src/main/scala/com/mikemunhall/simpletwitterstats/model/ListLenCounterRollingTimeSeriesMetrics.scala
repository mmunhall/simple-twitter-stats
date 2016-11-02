package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime

/**
  * A collection of metrics that tracks the number of occurrences of each element in a provided list
  *
  * @param label
  * @param default
  * @tparam `scala.Long`
  */
class ListLenCounterRollingTimeSeriesMetrics[`scala.Long`](label: String, default: () => scala.Long) extends RollingTimeSeriesMetrics(label, default) {
  def add(timestamp: LocalDateTime, obj: Any) = {
    // TODO: Understand why Long is cast to String. scala.Long is not cast.
    if (obj.asInstanceOf[List[String]].size > 0) values(timestamp.getHour)(timestamp.getMinute)(timestamp.getSecond) += 1
  }
}