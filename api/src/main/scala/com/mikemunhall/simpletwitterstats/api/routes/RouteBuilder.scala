package com.mikemunhall.simpletwitterstats.api.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.typesafe.scalalogging.StrictLogging

object RouteBuilder extends StrictLogging {

  def build: Route = {

    get {
      path("api" / "0.0.1" / "stats" ) {
        complete(StatusCodes.OK)
      }
    }
  }
}
