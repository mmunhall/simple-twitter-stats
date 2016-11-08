package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime

import com.mikemunhall.simpletwitterstats.model.UnitSpec

import scala.collection.mutable

class OccurrenceCounterRollingTimeSeriesMetricsSpec extends UnitSpec {

  "OccurrenceCounterRollingTimeSeriesMetrics" should "increment in the correct slot" in {
    val underTest = new OccurrenceRollingTimeSeriesMetrics("test", () => mutable.Map[String, Long]())
    val timestamp = LocalDateTime.of(2016, 10, 31, 17, 11, 22)
    underTest.values(17)(11)(22) shouldBe empty
    underTest.add(timestamp, List("1", "2"))
    underTest.values(17)(11)(22) shouldBe Map("1" -> 1, "2" -> 1)
    underTest.add(timestamp, List())
    underTest.values(17)(11)(22) shouldBe Map("1" -> 1, "2" -> 1)
    underTest.add(timestamp, List("2", "3"))
    underTest.values(17)(11)(22) shouldBe Map("1" -> 1, "2" -> 2, "3" -> 1)
    underTest.values(0)(12)(19) shouldBe empty
  }
}
