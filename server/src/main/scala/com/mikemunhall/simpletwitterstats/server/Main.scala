package com.mikemunhall.simpletwitterstats.server

import akka.actor.ActorSystem
import com.mikemunhall.simpletwitterstats.Settings
import com.mikemunhall.simpletwitterstats.api.APIService
import com.mikemunhall.simpletwitterstats.server.twitter.client.BasicTwitterClient
import com.typesafe.scalalogging.StrictLogging

object Main extends App with StrictLogging {

  logger.info("Starting application")

  // Typical actor setup
  implicit val system = ActorSystem("simple-twitter-stats")
  val settings = Settings(system)

  // Start Twitter client
  val parsingActor = system.actorOf(ParsingActor.props, "parsingActor")
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
