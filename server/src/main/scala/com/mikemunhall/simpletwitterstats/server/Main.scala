package com.mikemunhall.simpletwitterstats.server

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import com.mikemunhall.simpletwitterstats.Settings
import com.mikemunhall.simpletwitterstats.api.APIService
import com.mikemunhall.simpletwitterstats.server.twitter.client.BasicTwitterClient
import com.typesafe.scalalogging.StrictLogging

object Main extends App with StrictLogging {

  logger.info("Starting application")

  // Typical actor setup
  implicit val system = ActorSystem("simple-twitter-stats")
  val settings = Settings(system)

  val parsingActor = system.actorOf(FromConfig.props(Props(classOf[ParsingActor])), "parsingActor")
  val twitterClient = BasicTwitterClient(parsingActor, settings)
  val apiService = new APIService()

  twitterClient.start
  apiService.start

  sys addShutdownHook {
    twitterClient.stop
    apiService.stop

    logger.info("Stopping application")
  }
}
