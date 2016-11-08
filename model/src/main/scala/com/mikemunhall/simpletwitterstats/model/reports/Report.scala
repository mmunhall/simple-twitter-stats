package com.mikemunhall.simpletwitterstats.model.reports

/* Case classes to support easy marshaling to JSON

   The purpose of each class should be self-evident. Primitive types are used for properties to avoid needing to
   write entity marshalers right at this very moment.
*/

case class Header(startTimestamp: String, endTimestamp: String) // TODO: Write an entity marshaler for LocalDateTime

case class Occurrence(top: List[String], percentage: Double)

case class Report(
  report: Header,
  totalTweets: Long,
  averageTweetsPerSecond: Long,
  averageTweetsPerMinute: Long,
  averageTweetsPerHour: Long,
  emojis: Occurrence,
  hashtags: Occurrence,
  urls: Occurrence,
  photoUrls: Occurrence
)
