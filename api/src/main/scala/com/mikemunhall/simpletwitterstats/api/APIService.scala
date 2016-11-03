package com.mikemunhall.simpletwitterstats.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.StrictLogging
import com.mikemunhall.simpletwitterstats._
import com.mikemunhall.simpletwitterstats.api.routes.RouteBuilder
import scala.concurrent.ExecutionContext.Implicits.global

class APIService(implicit val system: ActorSystem) extends StrictLogging {

  def start = {
    logger.info("Starting API service")
  }

  def stop = {

  }

  implicit val materializer = ActorMaterializer()

  val settings = Settings(system)

  val host = settings.API.Http.Interface
  val port = settings.API.Http.Port

  // create top-level router actors
  // ...

  // create the HTTP service
  val route = RouteBuilder.build
  val bindingFuture = Http().bindAndHandle(route, host, port)

  bindingFuture onFailure {
    case ex: Exception =>
      logger.error(s"Failed to bind to host $host on port $port: ${ex.getMessage()}")
  }

  sys addShutdownHook {
    logger.info("Stopping API service")

    bindingFuture.flatMap(_.unbind()).onComplete { _ =>
      system.terminate()
    }
  }
}
