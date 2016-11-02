package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime
import scala.collection.mutable

/**
  * A collection of metrics that are incremented if a list contains values.
  *
  * @param label
  * @param default
  * @tparam `mutable.Map[String, Long]`
  */
class OccurrenceRollingTimeSeriesMetrics[`mutable.Map[String, Long]`](label: String, default: () => mutable.Map[String, Long]) extends RollingTimeSeriesMetrics(label, default) {
  def add(timestamp: LocalDateTime, obj: Any) = {
    obj.asInstanceOf[List[String]].foreach(item =>
      values(timestamp.getHour)(timestamp.getMinute)(timestamp.getSecond).get(item) match {
        case None => values(timestamp.getHour)(timestamp.getMinute)(timestamp.getSecond)(item) = 1
        case Some(_) => values(timestamp.getHour)(timestamp.getMinute)(timestamp.getSecond)(item) += 1
      }
    )
  }
}
