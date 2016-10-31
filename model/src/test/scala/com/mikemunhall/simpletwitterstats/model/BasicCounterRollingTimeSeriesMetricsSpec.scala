package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime

class BasicCounterRollingTimeSeriesMetricsSpec extends UnitSpec {

  "BasicCounterRollingTimeSeriesMetrics" should "increment in the correct slot" in {
    val underTest = new BasicCounterRollingTimeSeriesMetrics("test", () => 0)
    val timestamp = LocalDateTime.of(2016, 10, 31, 17, 11, 22)
    underTest.values(17)(11)(22) shouldBe 0
    underTest.add(timestamp, true)
    underTest.values(17)(11)(22) shouldBe 1
    underTest.add(timestamp, false)
    underTest.values(17)(11)(22) shouldBe 1
    underTest.add(timestamp, true)
    underTest.values(17)(11)(22) shouldBe 2
    underTest.values(0)(0)(0) shouldBe 0
    underTest.values(1)(2)(3) shouldBe 0
    underTest.values(23)(12)(22) shouldBe 0
  }
}
