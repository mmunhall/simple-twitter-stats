package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import java.util.function.BiFunction

/**
  * A collection of simple counter metrics.
  *
  * @param label
  * @param default
  * @tparam `scala.Long`
  */
class BasicCounterRollingTimeSeriesMetrics[`scala.Long`](label: String, default: () => scala.Long) extends RollingTimeSeriesMetrics(label, default) {
  val fn = new BiFunction[Int, Long, Long] {
    override def apply(t: Int, u: Long): Long = u + 1
  }

  def add(timestamp: LocalDateTime, obj: Any) = {
    if (obj.asInstanceOf[Boolean]) {
      values(timestamp.getHour)(timestamp.getMinute).compute(timestamp.getSecond, fn)
    }
  }
}