package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiFunction

/**
  * A collection of metrics that are incremented if a list contains values.
  *
  * @param label
  * @param default
  * @tparam `ConcurrentHashMap[String, Long]``
  */
class OccurrenceRollingTimeSeriesMetrics[`ConcurrentHashMap[String, Long]`](label: String, default: () => ConcurrentHashMap[String, Long]) extends RollingTimeSeriesMetrics(label, default) {
  val fn = new BiFunction[String, Long, Long] {
    override def apply(t: String, u: Long): Long = u + 1
  }

  def add(timestamp: LocalDateTime, obj: Any) = {
    obj.asInstanceOf[List[String]].foreach(item => {
      values(timestamp.getHour)(timestamp.getMinute).get(timestamp.getSecond).putIfAbsent(item, 0)
      values(timestamp.getHour)(timestamp.getMinute).get(timestamp.getSecond).compute(item, fn)
    })
  }
}
