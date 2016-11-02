package com.mikemunhall.simpletwitterstats.server

import akka.actor.{Actor, Props, Stash}
import com.mikemunhall.simpletwitterstats.model.Tweet
import com.mikemunhall.simpletwitterstats.server.util.ParseUtil
import com.typesafe.scalalogging.StrictLogging
import twitter4j.Status

object ParsingActor {
  def props = Props[ParsingActor]
  case class Parse(status: Status)
}

class ParsingActor extends Actor with Stash with StrictLogging with ParseUtil {
  import com.mikemunhall.simpletwitterstats.server.ParsingActor.Parse

  self ! "initialize"

  def receive = uninitialized

  def uninitialized: Receive = {
    case "initialize" =>
      logger.debug("Initializing ParsingActor and unstashing messages.")
      unstashAll()
      context.become(initialized)
    case _ =>
      logger.debug("ParsingActor invoked before initialization. Stashing message.")
      stash()
  }

  def initialized: Receive = {
    case Parse(status) =>
      val tweet = Tweet(
        dateToLocalDateTime(status.getCreatedAt),
        List(),
        status.getHashtagEntities.map(_.getText).toList,
        status.getURLEntities.map(_.getExpandedURL).filterNot(_ == "").map(domainFromUrl(_)).toList
      )
      println(tweet) // TODO: Remove me. Just debugging.
  }
}