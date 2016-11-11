package com.mikemunhall.simpletwitterstats.server

import java.util.Date
import akka.actor.Props
import akka.testkit.{TestActorRef, TestProbe}
import com.mikemunhall.simpletwitterstats.server.ParsingActor.Parse
import com.mikemunhall.simpletwitterstats.server.PersistingActor.Persist
import org.mockito.Mockito.when
import twitter4j.{HashtagEntity, Status, URLEntity}

class ParsingActorSpec extends ActorUnitSpec {

  val persistingActorProbe = TestProbe()

  val underTest = TestActorRef(Props(new ParsingActor {
    override val persistingActor = persistingActorProbe.ref
  }))

  "The actor" should "create a Tweet from a Status and delegate to the persisting actor" in {
    val status = mock[Status]
    when(status.getId).thenReturn(12345l)
    when(status.getCreatedAt).thenReturn(new Date())
    when(status.getText).thenReturn("hi")
    when(status.getHashtagEntities).thenReturn(Array[HashtagEntity]())
    when(status.getURLEntities).thenReturn(Array[URLEntity]())

    underTest ! Parse(status)

    val message = persistingActorProbe.expectMsgType[Persist]
    message.tweet.id should be (12345l)
  }
}
