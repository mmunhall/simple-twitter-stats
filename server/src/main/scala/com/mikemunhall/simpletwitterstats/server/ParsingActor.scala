package com.mikemunhall.simpletwitterstats.server

import akka.actor.{Actor, Props, Stash}
import com.mikemunhall.simpletwitterstats.model.metrics.Tweet
import com.mikemunhall.simpletwitterstats.server.util.ParseUtil
import com.typesafe.scalalogging.StrictLogging
import twitter4j.Status

object ParsingActor {
  def props = Props[ParsingActor]
  case class Parse(status: Status)
}

class ParsingActor extends Actor with Stash with StrictLogging with ParseUtil {
  import com.mikemunhall.simpletwitterstats.server.ParsingActor.Parse
  import com.mikemunhall.simpletwitterstats.server.PersistingActor.Persist

  val persistingActor = context.actorOf(PersistingActor.props, "persistingActor")

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
        status.getId,
        dateToLocalDateTime(status.getCreatedAt),
        emojisFromText(status.getText),
        status.getHashtagEntities.map(_.getText).toList,
        status.getURLEntities.map(_.getExpandedURL).filterNot(_ == "").map(domainFromUrl(_)).toList,
        status.getURLEntities.map(_.getExpandedURL).filter(d => d.contains("pic.twitter") || d.contains("instagram")).map(domainFromUrl(_)).toList
      )
      persistingActor ! Persist(tweet)
  }
}