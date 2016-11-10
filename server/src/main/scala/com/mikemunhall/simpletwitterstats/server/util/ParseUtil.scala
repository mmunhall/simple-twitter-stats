package com.mikemunhall.simpletwitterstats.server.util

import java.time.{Instant, LocalDateTime, ZoneId}
import java.util.Date
import java.net.URL
import java.text.BreakIterator

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

  // TODO: Terribly inefficient and brute force. Cleanup, refactor.
  def emojisFromText(text: String): List[String] = {
    val boundary = BreakIterator.getCharacterInstance();
    boundary setText text

    val normalized = new ListBuffer[String]()
    var start = boundary.first()
    var end = boundary.next()
    while (end != BreakIterator.DONE) {
      normalized += text.substring(start,end)
      start = end
      end = boundary.next()
    }

    val emojisList = new ListBuffer[String]()
    emojiMap.keys.foreach(k => {
      normalized.foreach(ns => {
        if (k == ns) {
          emojisList += emojiMap(k)
        }
      })
    })

    emojisList.toList
  }

  // TODO: This is very brute force. Cleanup, refactor.
  private def makeMap(): Map[String, String] = {
    val json = parse(Source.fromURL(getClass.getResource("/emoji.json")).mkString)
    val emojiStrings = (json \ "unified").values.asInstanceOf[List[String]].map(e => {
      val points = e.split("-").map(str => Integer.parseInt(str, 16))
      new String(points, 0, points.length)
    })
    val emojiNames = (json \ "name").values.asInstanceOf[List[String]]
    val lookupMap = emojiStrings zip emojiNames
    lookupMap.par.foldLeft(Map[String, String]())((acc, v) => acc + (v._1 -> v._2))
  }
}
