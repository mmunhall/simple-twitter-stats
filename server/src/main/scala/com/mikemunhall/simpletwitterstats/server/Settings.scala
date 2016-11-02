package com.mikemunhall.simpletwitterstats.server

import akka.actor.{ActorContext, ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider}
import com.typesafe.config.Config

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {

  override def lookup = Settings
  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config, system)
  def apply(context: ActorContext): Settings = apply(context.system)
}

class Settings(config: Config, extendedSystem: ExtendedActorSystem) extends Extension {

  object Client {
    object Http {
      val Port = config.getInt("simple-twitter-stats.client.http.port")
      val Interface = config.getString("simple-twitter-stats.client.http.interface")
    }
  }

  object Server {
    object Twitter {
      object Client {
        object OAuth {
          object Consumer {
            val key = config.getString("simple-twitter-stats.server.twitter.client.oauth.consumer.key")
            val secret = config.getString("simple-twitter-stats.server.twitter.client.oauth.consumer.secret")
          }
          object Access {
            val token = config.getString("simple-twitter-stats.server.twitter.client.oauth.access.token")
            val secret = config.getString("simple-twitter-stats.server.twitter.client.oauth.access.secret")
          }
        }
      }
    }
  }

}
