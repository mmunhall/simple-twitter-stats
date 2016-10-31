package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime

case class Tweet(timestamp: LocalDateTime, emojis: List[String], hashtags: List[String], domains: List[String])