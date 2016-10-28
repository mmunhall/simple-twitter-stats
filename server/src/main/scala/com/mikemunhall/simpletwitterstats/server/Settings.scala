package com.mikemunhall.simpletwitterstats.server

import java.util.concurrent.TimeUnit

import akka.actor.{ActorContext, ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider}
import com.typesafe.config.Config

import scala.concurrent.duration.FiniteDuration

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def lookup = Settings
  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config, system)

  def apply(context: ActorContext): Settings = apply(context.system)
}

class Settings(config: Config, extendedSystem: ExtendedActorSystem) extends Extension {
  object Http {
    val Port = config.getInt("simple-twitter-stats.http.port")
    val Interface = config.getString("simple-twitter-stats.http.interface")
  }

  val askTimeout = FiniteDuration(config.getDuration("simple-twitter-stats.ask-timeout", TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS)
}
