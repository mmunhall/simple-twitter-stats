package com.mikemunhall.simpletwitterstats.api.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.mikemunhall.simpletwitterstats.api.ReportBuilder
import com.mikemunhall.simpletwitterstats.model.reports.{Header, Occurrence, Report}
import com.typesafe.scalalogging.StrictLogging
import spray.json._

object RouteBuilder extends SprayJsonSupport with DefaultJsonProtocol with StrictLogging {

  implicit val headerFormat = jsonFormat2(Header)
  implicit val occurrenceFormat = jsonFormat2(Occurrence)
  implicit val reportFormat = jsonFormat9(Report)

  def build: Route = {

    get {
      path("api" / "0.0.1" / "stats" ) {
        val report = ReportBuilder.build
        complete(report)
      }
    }
  }
}
