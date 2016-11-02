package com.mikemunhall.simpletwitterstats.server.twitter.client

import akka.actor.ActorRef
import com.mikemunhall.simpletwitterstats.server.ParsingActor.Parse
import com.mikemunhall.simpletwitterstats.server.Settings
import com.typesafe.scalalogging.StrictLogging
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

object BasicTwitterClient {
  def apply(parsingActor: ActorRef, settings: Settings) = new BasicTwitterClient(parsingActor, settings)
}

class BasicTwitterClient(parsingActor: ActorRef, settings: Settings) extends StrictLogging {

  val cb = new ConfigurationBuilder()
  cb.setDebugEnabled(true)
    .setOAuthConsumerKey(settings.Server.Twitter.Client.OAuth.Consumer.key)
    .setOAuthConsumerSecret(settings.Server.Twitter.Client.OAuth.Consumer.secret)
    .setOAuthAccessToken(settings.Server.Twitter.Client.OAuth.Access.token)
    .setOAuthAccessTokenSecret(settings.Server.Twitter.Client.OAuth.Access.secret)

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) = parsingActor ! Parse(status)
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

  val twitterStream = new TwitterStreamFactory(cb.build()).getInstance
  twitterStream.addListener(simpleStatusListener)

  def start = {
    logger.info("Starting Twitter stream")
    twitterStream.sample
  }

  def stop = {
    logger.info("Stopping Twitter stream")
    twitterStream.cleanUp
    twitterStream.shutdown
  }

}
