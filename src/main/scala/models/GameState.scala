package models

case class GameState(bankedPoints: Points, unbankedPoints: Points, currentBalloonCapacity: BalloonCapacity, otherBalloons: List[BalloonCapacity])

case class Points(value: Int) {

  def +(other: Points): Points = Points(value + other.value)

  def +(int: Int): Points = Points(value + int)
}

case class BalloonCapacity(value: Int) {

  def isNone: Boolean = value == 0

  def -(int: Int): BalloonCapacity = BalloonCapacity(value - int)

}