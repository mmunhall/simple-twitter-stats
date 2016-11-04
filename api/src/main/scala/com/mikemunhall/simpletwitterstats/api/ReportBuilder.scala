package com.mikemunhall.simpletwitterstats.api

import com.mikemunhall.simpletwitterstats.timeSeriesData
import com.mikemunhall.simpletwitterstats.model.reports.{Occurrence, Report, ReportTimespan}

object ReportBuilder {

  def build: Report = {
    val tweetsPerSecond = timeSeriesData.tweets.values

    Report(ReportTimespan(0, 0, 0), 0, 0, 0, 0, Occurrence(List(), 0), Occurrence(List(), 0), Occurrence(List(), 0), Occurrence(List(), 0))
  }

}
