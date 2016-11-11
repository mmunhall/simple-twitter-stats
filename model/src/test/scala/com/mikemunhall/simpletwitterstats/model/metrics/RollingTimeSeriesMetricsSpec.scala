package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import com.mikemunhall.simpletwitterstats.model.UnitSpec
import collection.JavaConverters._

class RollingTimeSeriesMetricsSpec extends UnitSpec {

  "RollingTimeSeriesMetrics implementations" should "be initialized with 24-hours of slots at 1-second resolution" in {
    class testClass extends RollingTimeSeriesMetrics[Int]("test", () => 22) {
      def add(timestamp: LocalDateTime, obj: Any) { }
    }
    val underTest = new testClass
    underTest.label should be ("test")
    underTest.values should have size 24
    underTest.values.keys.foreach(n => {
      underTest.values(n) should have size 60
      underTest.values(n).keys.foreach(m => {
        underTest.values(n)(m) should have size 60
        underTest.values(n)(m).keys().asScala.foreach(o => {
          underTest.values(n)(m).get(0) shouldBe 22
        })
      })
    })
  }

}
