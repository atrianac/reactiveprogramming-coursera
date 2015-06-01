package scala.coursera.actors

import akka.actor.Actor
import akka.actor.Props
import akka.event.LoggingReceive

/**
 * @author Alejandro
 */
class TransferMain extends Actor {

  val accountA = context.actorOf(Props[BankAccount], "accountA")

  val accountB = context.actorOf(Props[BankAccount], "accountB")

  accountA ! BankAccount.Deposit(100)

  def receive = LoggingReceive {
    case BankAccount.Done => transfer(50)
  }

  def transfer(amount: BigInt): Unit = {
    val transaction = context.actorOf(Props[WireTransfer], "transfer")
    transaction ! WireTransfer.Transfer(accountA, accountB, amount)
    context.become(LoggingReceive {
      case WireTransfer.Done =>
        print("sucess")
        context.stop(self)
    })
  }

}