package utils

import models.Points

import scala.io.StdIn

class IOHelper {

  def requestBalloons(): Unit = println("Enter balloons eg. 1 7 2 6:")

  def complain(): Unit = println("I don't understand")

  def requestInflateOrBank(): Unit = println("Enter INFLATE or BANK:")

  def readInput: String = StdIn.readLine()

  def printBurst(): Unit = println("BURST")

  def printScore(points: Points): Unit = println(asScore(points))

  def asScore(points: Points): String = s"SCORE: ${points.value}"
}
