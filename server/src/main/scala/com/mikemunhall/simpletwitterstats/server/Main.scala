package com.mikemunhall.simpletwitterstats.server

import akka.actor.ActorSystem
import com.mikemunhall.simpletwitterstats.model.TwitterTimeSeriesData
import com.typesafe.scalalogging.StrictLogging
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

object Main extends App with StrictLogging {

  logger.info("Starting application")

  implicit val system = ActorSystem("simple-twitter-stats")
  val settings = Settings(system)

  val timeSeriesData = TwitterTimeSeriesData()

  val cb = new ConfigurationBuilder()
  cb.setDebugEnabled(true)
    .setOAuthConsumerKey(settings.Server.Twitter.Client.OAuth.Consumer.key)
    .setOAuthConsumerSecret(settings.Server.Twitter.Client.OAuth.Consumer.secret)
    .setOAuthAccessToken(settings.Server.Twitter.Client.OAuth.Access.token)
    .setOAuthAccessTokenSecret(settings.Server.Twitter.Client.OAuth.Access.secret)
  val config = cb.build()

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) { println(status.getText) }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

  val twitterStream = new TwitterStreamFactory(config).getInstance
  twitterStream.addListener(simpleStatusListener)
  twitterStream.sample
  Thread.sleep(2000)
  twitterStream.cleanUp
  twitterStream.shutdown

  sys addShutdownHook {
    logger.info("Stopping application")
  }
}
