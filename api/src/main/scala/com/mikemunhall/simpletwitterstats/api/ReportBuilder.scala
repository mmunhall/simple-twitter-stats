package com.mikemunhall.simpletwitterstats.api

import com.mikemunhall.simpletwitterstats.timeSeriesData
import com.mikemunhall.simpletwitterstats.model.reports.{Header, Occurrence, Report}

object ReportBuilder {

  def build: Report = {
    val startTimestamp = timeSeriesData.startTimestamp.toString
    val endTimestamp = timeSeriesData.endTimestamp.toString
    val tweetsPerSecond = timeSeriesData.tweets.values

    Report(Header(startTimestamp, endTimestamp), 0, 0, 0, 0, Occurrence(List(), 0), Occurrence(List(), 0), Occurrence(List(), 0), Occurrence(List(), 0))
  }

}
