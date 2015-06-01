package scala.coursera.actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

class Counter extends Actor {
  
  def counter(n: Int): Receive = {
    case "incr" => context.become(counter(n + 1))
    case "get" => sender ! n
  }   
    
  def receive = counter(0)
  
}

object Counter {
  
  def props(init: Int): Props = Props(new Counter())
}