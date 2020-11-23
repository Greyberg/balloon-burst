import models._
import utils.{IOHelper, ValidationHelper}

import scala.annotation.tailrec

class BalloonBurst(ioHelper: IOHelper, validationHelper: ValidationHelper) {

  import ioHelper._
  import validationHelper._

  @tailrec
  final def runGame(): Unit = {

    requestBalloons()

    val initialBalloons: Option[List[BalloonCapacity]] = asBalloonList(readInput)

    initialBalloons match {
      case Some(list) => runGameIteration(GameState(Points(0), Points(0), list.head, list.tail))
      case None =>
        complain()
        runGame()
    }

  }

  @tailrec
  final def runGameIteration(gameState: GameState): Unit = {
    val bankedPoints = gameState.bankedPoints
    val unbankedPoints = gameState.unbankedPoints
    val currentBalloonCapacity = gameState.currentBalloonCapacity
    val otherBalloons = gameState.otherBalloons

    requestInflateOrBank()

    isInflate(readInput) match {
      case Some(inflate) =>
        if (inflate) {
          //The user has chosen to inflate the balloon
          if (currentBalloonCapacity.isNone) {
            //The balloon bursts
            printBurst()
            otherBalloons match {
              case Nil =>
                //There are no more balloons left so we print the final banked points
                printScore(bankedPoints)
              case head :: tail =>
                //We lose the unbanked score and start the next iteration with the next balloon
                val newGameState = GameState(bankedPoints, Points(0), head, tail)
                runGameIteration(newGameState)
            }

          } else {
            //The balloon successfully inflates. Add one to the unbanked score and reduce the current balloon capacity by 1
            val newUnbankedPoints = unbankedPoints + 1
            val newBalloonCapacity = currentBalloonCapacity - 1
            val newGameState = gameState.copy(unbankedPoints = newUnbankedPoints, currentBalloonCapacity = newBalloonCapacity)

            runGameIteration(newGameState)
          }
        } else {
          //The user has chosen to bank the score
          otherBalloons match {
            case Nil =>
              //There are no more balloons left so we print the final score plus the points just banked
              printScore(bankedPoints + unbankedPoints)
            case head :: tail =>
              //We add the unbanked score and start the next iteration with the next balloon
              val newGameState = GameState(bankedPoints + unbankedPoints, Points(0), head, tail)
              runGameIteration(newGameState)
          }
        }
      case None =>
        //The user has written something incomprehensible
        complain()
        runGameIteration(gameState)
    }
  }

}

object BalloonBurst extends BalloonBurst(new IOHelper, new ValidationHelper) with App {

  runGame()

}



