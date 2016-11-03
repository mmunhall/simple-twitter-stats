package com.mikemunhall.simpletwitterstats.server

import akka.actor.{Actor, Props, Stash}
import com.mikemunhall.simpletwitterstats.model.Tweet
import com.mikemunhall.simpletwitterstats.server.util.ParseUtil
import com.typesafe.scalalogging.StrictLogging

object PersistingActor {
  def props = Props[PersistingActor]
  case class Persist(tweet: Tweet)
}

class PersistingActor extends Actor with Stash with StrictLogging with ParseUtil {
  import com.mikemunhall.simpletwitterstats.server.PersistingActor.Persist
  import com.mikemunhall.simpletwitterstats.timeSeriesData

  self ! "initialize"

  def receive = uninitialized

  def uninitialized: Receive = {
    case "initialize" =>
      logger.debug("Initializing PersistingActor and unstashing messages.")
      unstashAll()
      context.become(initialized)
    case _ =>
      logger.debug("PersistingActor invoked before initialization. Stashing message.")
      stash()
  }

  def initialized: Receive = {
    case Persist(tweet) =>
      timeSeriesData.add(tweet)
  }
}