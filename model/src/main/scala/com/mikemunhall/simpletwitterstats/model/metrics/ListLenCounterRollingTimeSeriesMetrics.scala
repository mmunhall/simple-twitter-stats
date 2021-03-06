package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import java.util.function.BiFunction

/**
  * A collection of metrics that tracks the number of occurrences of each element in a provided list
  *
  * @param label
  * @param default
  * @tparam `scala.Long`
  */
class ListLenCounterRollingTimeSeriesMetrics[`scala.Long`](label: String, default: () => scala.Long) extends RollingTimeSeriesMetrics(label, default) {
  val fn = new BiFunction[Int, Long, Long] {
    override def apply(t: Int, u: Long): Long = u + 1
  }

  def add(timestamp: LocalDateTime, obj: Any) = {
    // TODO: Understand why Long is cast to String. scala.Long is not cast.

    if (obj.asInstanceOf[List[String]].size > 0) {
      values(timestamp.getHour)(timestamp.getMinute).putIfAbsent(timestamp.getSecond, 0)
      values(timestamp.getHour)(timestamp.getMinute).compute(timestamp.getSecond, fn)
    }
  }
}