package com.mikemunhall.simpletwitterstats.model.metrics

import java.time.{Duration, LocalDateTime}

/**
  * Functions to calculate 24-hour rolling period start and end dates
  */
trait RollingDateTimeCalculations {
  def calculateNewStartDateTime(testDateTime: LocalDateTime, currentStartDateTime: LocalDateTime, currentEndDateTime: LocalDateTime) = {
    (testDateTime, currentStartDateTime, currentEndDateTime) match {
      case (test, _, end) if Duration.between(test, end).getSeconds() > 86400 => end.minusSeconds(86400)
      case (test, start, _) if test.isBefore(start) => test
      case _ => currentStartDateTime
    }
  }

  def calculateNewEndDateTime(testDateTime: LocalDateTime, currentEndDateTime: LocalDateTime) = {
    (testDateTime, currentEndDateTime) match {
      case (test, end) if test.isAfter(end) => test
      case _ => currentEndDateTime
    }
  }
}