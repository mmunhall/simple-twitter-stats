package com.mikemunhall.simpletwitterstats.server

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.mock.MockitoSugar

class ActorUnitSpec extends UnitSpec with MockitoSugar with BeforeAndAfterAll with BeforeAndAfter {

  implicit val system = ActorSystem("simple-twitter-stats-test")

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

}
