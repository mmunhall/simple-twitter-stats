package com.mikemunhall.simpletwitterstats.server.util

import java.time.{Instant, LocalDateTime, ZoneId}
import java.util.Date
import java.net.URL
import com.typesafe.scalalogging.StrictLogging

trait ParseUtil extends StrictLogging {

  def dateToLocalDateTime(date: Date): LocalDateTime = {
    val instant = Instant.ofEpochMilli(date.getTime());
    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
  }

  def domainFromUrl(url: String): String = {
    new URL(url).getHost
  }
}
