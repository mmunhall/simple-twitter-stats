package com.mikemunhall

import com.mikemunhall.simpletwitterstats.model.metrics.TwitterTimeSeriesData

package object simpletwitterstats {

  // timeSeriesData is a global object containing all metrics, stored in memory.
  val timeSeriesData = TwitterTimeSeriesData()
}
