package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime

/**
  * Contains the parsed elements of a Twitter status, each of which are tracked by one or more RollingTimeSeriesMetrics.
  *
  * @param timestamp
  * @param emojis
  * @param hashtags
  * @param domains
  */
case class Tweet(timestamp: LocalDateTime, emojis: List[String], hashtags: List[String], domains: List[String])