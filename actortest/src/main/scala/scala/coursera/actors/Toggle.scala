package scala.coursera.actors

import akka.actor.Actor

class Toggle extends Actor {
  
  def happy: Receive = {
    case "How are you?" =>
      sender ! "happy"
      context become sad
  }
  
  def sad: Receive = {
    case "How are you?" =>
      sender ! "sad"
      context become happy
  }
  
  def receive = happy
  
}