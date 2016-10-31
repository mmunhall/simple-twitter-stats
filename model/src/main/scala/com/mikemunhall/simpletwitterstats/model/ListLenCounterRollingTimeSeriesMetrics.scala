package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime


class ListLenCounterRollingTimeSeriesMetrics[`scala.Long`](label: String, default: () => scala.Long) extends RollingTimeSeriesMetrics(label, default) {
  def add(timestamp: LocalDateTime, obj: Any) = {
    if (obj.asInstanceOf[List[String]].size > 0) values(timestamp.getHour)(timestamp.getMinute)(timestamp.getSecond) += 1
  }
}