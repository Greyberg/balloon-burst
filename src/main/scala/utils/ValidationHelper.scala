package utils

import models.BalloonCapacity

class ValidationHelper {

  def asBalloonList(string: String): Option[List[BalloonCapacity]] = {
    try {
      val initialList: List[BalloonCapacity] = string
        .trim
        .split(" ")
        .map(s => BalloonCapacity(s.toInt))
        .toList

      if (initialList.forall(_.value >= 0)) {
        Some(initialList)
      } else None

    } catch {
      case e: Exception => None
    }
  }

  def isInflate(string: String): Option[Boolean] = {
    if (string == "INFLATE") Some(true)
    else if (string == "BANK") Some(false)
    else None
  }

}
