package com.mikemunhall.simpletwitterstats.api

import java.time.LocalDateTime
import com.mikemunhall.simpletwitterstats.timeSeriesData

class ReportBuilderSpec extends UnitSpec {

  trait Fixtures {
    val timestamp1 = LocalDateTime.of(1973, 2, 12, 19, 11, 0)
    val timestamp2 = LocalDateTime.of(1973, 2, 12, 19, 12, 0)
    val timestamp3 = LocalDateTime.of(1973, 2, 12, 19, 13, 0)
    val timestamp4 = LocalDateTime.of(1973, 2, 12, 20, 11, 0)
    val timestamp5 = LocalDateTime.of(1973, 2, 12, 20, 12, 0)
    val timestamp6 = LocalDateTime.of(1973, 2, 12, 20, 13, 0)
    timeSeriesData.hashtags.add(timestamp1, List("A", "C"))
    timeSeriesData.hashtags.add(timestamp1, List("A", "B", "C"))
    timeSeriesData.hashtags.add(timestamp1, List("A"))
    timeSeriesData.hashtags.add(timestamp2, List("C", "D"))
    timeSeriesData.hashtags.add(timestamp2, List("C", "D"))
    timeSeriesData.hashtags.add(timestamp2, List("E", "B"))
    timeSeriesData.hashtags.add(timestamp3, List("B", "B", "B"))
  }

  "ReportBuilder" should "list top hashtags in order of occurrence" in new Fixtures {
    val underTest = ReportBuilder.build
    underTest.hashtags.top should be (List("B", "C", "A", "D", "E"))
  }
}
