package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime

/**
  * A collection of simple counter metrics.
  *
  * @param label
  * @param default
  * @tparam `scala.Long`
  */
class BasicCounterRollingTimeSeriesMetrics[`scala.Long`](label: String, default: () => scala.Long) extends RollingTimeSeriesMetrics(label, default) {
  def add(timestamp: LocalDateTime, obj: Any) = {
    if (obj.asInstanceOf[Boolean]) values(timestamp.getHour)(timestamp.getMinute)(timestamp.getSecond) += 1
  }
}