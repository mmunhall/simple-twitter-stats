package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime
import scala.collection.mutable

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
