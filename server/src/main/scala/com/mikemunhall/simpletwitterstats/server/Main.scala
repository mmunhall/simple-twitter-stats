package com.mikemunhall.simpletwitterstats.server

import akka.actor.ActorSystem
import com.mikemunhall.simpletwitterstats.model.TwitterTimeSeriesData
import com.mikemunhall.simpletwitterstats.server.twitter.client.BasicTwitterClient
import com.typesafe.scalalogging.StrictLogging

object Main extends App with StrictLogging {

  logger.info("Starting application")

  // Typical actor setup
  implicit val system = ActorSystem("simple-twitter-stats")
  val settings = Settings(system)

  // timeSeriesData is a global object containing all metrics, stored in memory.
  val timeSeriesData = TwitterTimeSeriesData()

  // Start Twitter client
  val parsingActor = system.actorOf(ParsingActor.props, "parsingActor")
  val twitterClient = BasicTwitterClient(parsingActor, settings)
  twitterClient.start

  sys addShutdownHook {
    twitterClient.stop // gracefully shuts down Twitter client
    logger.info("Stopping application")
  }
}
