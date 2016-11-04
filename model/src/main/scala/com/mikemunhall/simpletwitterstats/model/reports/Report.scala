package com.mikemunhall.simpletwitterstats.model.reports

case class Report(
  timespan: ReportTimespan,
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
