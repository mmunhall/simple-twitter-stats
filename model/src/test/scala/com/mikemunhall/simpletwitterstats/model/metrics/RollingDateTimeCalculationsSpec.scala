package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.LocalDateTime
import com.mikemunhall.simpletwitterstats.model.UnitSpec

class RollingDateTimeCalculationsSpec extends UnitSpec {

  val underTest = new AnyRef with RollingDateTimeCalculations

  "calculateNewStartDateTime" should "roll the start datetime if test datetime and current end datetime diff by more than 1 day" in {
    val startDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val testDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val endDateTime = LocalDateTime.of(2016, 11, 9, 19, 11, 1)
    val result = underTest.calculateNewStartDateTime(testDateTime, startDateTime, endDateTime)
    result.compareTo(endDateTime.minusSeconds(86400)) shouldBe 0
  }

  it should "set start datetime to test datetime if exactly 1 day difference" in {
    val startDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val testDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val endDateTime = LocalDateTime.of(2016, 11, 9, 19, 11, 1)
    val result = underTest.calculateNewStartDateTime(testDateTime, startDateTime, endDateTime)
    result.compareTo(endDateTime.minusSeconds(86400)) shouldBe 0
  }

  it should "set start to test datetime if less than 1 day difference and test datetime is before current start datetime" in {
    val startDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val testDateTime = LocalDateTime.of(2016, 11, 8, 19, 10, 59)
    val endDateTime = LocalDateTime.of(2016, 11, 8, 20, 11, 0)
    val result = underTest.calculateNewStartDateTime(testDateTime, startDateTime, endDateTime)
    result.compareTo(testDateTime) shouldBe 0
  }

  it should "not change start datetime if test datetime is after current start datetime" in {
    val startDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val testDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 1)
    val endDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val result = underTest.calculateNewStartDateTime(testDateTime, startDateTime, endDateTime)
    result.compareTo(startDateTime) shouldBe 0
  }

  "calculateNewEndDateTime" should "set end to test datetime if less than 1 day difference and test datetime is after current end datetime" in {
    val testDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 1)
    val endDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val result = underTest.calculateNewEndDateTime(testDateTime, endDateTime)
    result.compareTo(testDateTime) shouldBe 0
  }

  it should "not change end datetime if test datetime is before current end datetime" in {
    val testDateTime = LocalDateTime.of(2016, 11, 8, 19, 10, 59)
    val endDateTime = LocalDateTime.of(2016, 11, 8, 19, 11, 0)
    val result = underTest.calculateNewEndDateTime(testDateTime, endDateTime)
    result.compareTo(endDateTime) shouldBe 0
  }
}