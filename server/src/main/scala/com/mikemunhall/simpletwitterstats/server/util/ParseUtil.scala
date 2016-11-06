package com.mikemunhall.simpletwitterstats.server.util

import java.time.{Instant, LocalDateTime, ZoneId}
import java.util.Date
import java.net.URL
import com.typesafe.scalalogging.StrictLogging
import org.json4s.native.JsonMethods._
import scala.collection.mutable.ListBuffer
import scala.io.Source

trait ParseUtil extends StrictLogging {

  val emojiMap = makeMap()

  def dateToLocalDateTime(date: Date): LocalDateTime = {
    val instant = Instant.ofEpochMilli(date.getTime());
    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
  }

  def domainFromUrl(url: String): String = {
    new URL(url).getHost
  }

  // TODO: Not working, and in any case is too ineffecient.
  def emojisFromText(text: String): List[String] = {
    /*val emojis = ListBuffer[String]()
    emojiMap.keys.foreach(k => {
      val count = text.count(_ == k.toCharArray()(0))
      for(i <- 1 to count) {
        emojis += emojiMap(k)
      }
    })
    emojis.toList*/
    List()
  }

  // TODO: Not entirely sure code points are being mapped correctly.
  private def makeMap(): Map[String, String] = {
    val json = parse(Source.fromURL(getClass.getResource("/emoji.json")).mkString) // TODO: Externalize reference to emoji data
    val k = (json \ "unified").values.asInstanceOf[List[String]].map(e => {
      val codepoints = e.split("-").map(str => Integer.parseInt(str, 16))
      new String(codepoints, 0, codepoints.length)
    })
    val v = (json \ "name").values.asInstanceOf[List[String]]
    val zipped = k zip v
    zipped.par.foldLeft(Map[String, String]())((acc, v) => acc + (v._1 -> v._2))
  }
}
