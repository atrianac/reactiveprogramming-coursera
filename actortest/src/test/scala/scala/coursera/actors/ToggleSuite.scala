package scala.coursera.actors

import org.junit.Test
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestProbe


class ToggleSuite {
  
  @Test
  def testMessage() {
    implicit val system = ActorSystem("TestSys")
    val toggle = system.actorOf(Props[Toggle])
    val p = TestProbe()
    
    p.send(toggle, "How are you?")
    p.expectMsg("happy")
    p.send(toggle, "How are you?")
    p.expectMsg("sad")
    p.send(toggle, "unknown")
    p.expectNoMsg()
    system.shutdown()
  }
  
}