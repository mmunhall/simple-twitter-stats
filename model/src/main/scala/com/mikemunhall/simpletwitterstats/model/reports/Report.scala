package com.mikemunhall.simpletwitterstats.model.reports

/* Case classes to support easy marshaling to JSON

   The purpose of each class should be self-evident. Primitive types are used for properties to avoid needing to
   write entity marshalers right at this very moment.
*/

case class Header(startTimestamp: String, endTimestamp: String) // TODO: Write an entity marshaler for LocalDateTime
case class Occurrence(top: List[String], percentage: Int)
case class Report(
  report: Header,
  totalTweets: Long,
  averageTweetsPerHour: Long,
  averageTweetsPerMinute: Long,
  averageTweetsPerSecond: Long,
  emojis: Occurrence,
  hashtags: Occurrence,
  urls: Occurrence,
  photoUrls: Occurrence
)

//{
//    "dataTo": "2016-11-03T15:19:22",
//    "totalTweets": 437289,
//    "averageTweetsPerHour": 2222,
//    "averageTweetsPerMinute": 222,
//    "averageTweetsPerSecond": 57,
//    "emojis": {
//        "top": ["top0", "top1", "top2", "top3", "top4", "top5", "top6", "top7", "top8", "top9"],
//        "tweetPercentage": 22
//    },
//    "hashtags": {
//        "top": ["top0", "top1", "top2", "top3", "top4", "top5", "top6", "top7", "top8", "top9"],
//        "tweetPercentage": 31
//    },
//    "urls": {
//        "top": ["top0", "top1", "top2", "top3", "top4", "top5", "top6", "top7", "top8", "top9"],
//        "tweetPercentage": 31
//    },
//    "photoUrls: {
//        "top": ["top0", "top1", "top2", "top3", "top4", "top5", "top6", "top7", "top8", "top9"],
//        "tweetPercentage": 2
//    }
//}
