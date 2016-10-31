package com.mikemunhall.simpletwitterstats.model

import java.time.LocalDateTime
import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * This test of a model class is largely unnecessary, but I'm providing it as a sanity check for myself.
  */
class MetricSpec extends UnitSpec {

  trait Fixtures {
    val tweetText =
      """"created_at"
         {
            "created_at": "Sun Oct 30 23:48:36 +0000 2016",
            "entities": {
              "urls": ["example.com", "example.org"],
              "hashtags": ["foo"]
            }
         }
      """.stripMargin
  }

  "A counter metric" should "increment a count" in new Fixtures {
    def add(tweet: Tweet): Unit = {
      println("foo")
    }
    val m = new CounterRollingTimeSeriesMetrics("sample", () => 0, add)
    m.add(Tweet(LocalDateTime.now(), List.empty, List.empty, List.empty))
  }

  "tweetTotals" should "do all the tings" in new Fixtures {
    val data = new TwitterTimeSeriesData
    data.tweetTotals.add(Tweet(LocalDateTime.now(), List.empty, List.empty, List.empty))
  }

  /*trait Fixtures {
    val timestamp = LocalDateTime.of(1973, 2, 12, 19, 11, 0)
    val metric = new Metric(timestamp)
  }

  "Metric" should "be instantiated with a timestamp" in new Fixtures {
    metric.timestamp shouldBe timestamp
  }

  it should "be instantiated with default values" in new Fixtures {
    metric.total shouldBe 0
    metric.emojis.size shouldBe 0
    metric.hashtags.size shouldBe 0
    metric.urls.size shouldBe 0
    metric.withEmojis shouldBe 0
    metric.withUrls shouldBe 0
    metric.withPhotoUrls shouldBe 0
  }

  it should "allow increment of the total property" in new Fixtures {
    metric.total shouldBe 0
    metric.total += 1
    metric.total shouldBe 1
  }

  it should "allow additions to the emojis map" in new Fixtures {
    metric.emojis.size shouldBe 0
    metric.emojis.get("foo") match {
      case None => metric.emojis.put("foo", 1)
      case Some(v) => metric.emojis("foo") += 1
    }
    metric.emojis.size shouldBe 1
    metric.emojis("foo") shouldBe 1
  }*/
}
