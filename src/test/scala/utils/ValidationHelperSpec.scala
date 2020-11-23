package utils

import models.BalloonCapacity
import org.scalatest.flatspec._
import org.scalatest.matchers._

class ValidationHelperSpec extends AnyFlatSpec with should.Matchers {

  val sut = new ValidationHelper

  "asBalloonList" should "return None for an empty list" in {
    sut.asBalloonList("") shouldBe None
  }

  "asBalloonList" should "return None if any number is greater than an Int" in {
    sut.asBalloonList("7 2147483648 0") shouldBe None
  }

  "asBalloonList" should "return None for gibberish" in {
    sut.asBalloonList("gibberish") shouldBe None
  }

  "asBalloonList" should "reject any negative numbers" in {
    sut.asBalloonList("-2 3 3") shouldBe None
  }

  "asBalloonList" should "allow a single balloon capacity" in {
    sut.asBalloonList("1") shouldBe Some(List(BalloonCapacity(1)))
  }

  "asBalloonList" should "allow 0 balloon capacity" in {
    sut.asBalloonList("2 3 0") shouldBe Some(List(BalloonCapacity(2), BalloonCapacity(3), BalloonCapacity(0)))
  }

  "asBalloonList" should "all high capacity input" in {
    sut.asBalloonList("3 2147483647 1") shouldBe Some(List(BalloonCapacity(3), BalloonCapacity(2147483647), BalloonCapacity(1)))
  }

  "asBalloonList" should "allow valid input in need of a trim" in {
    sut.asBalloonList(" 3 2 1 ") shouldBe Some(List(BalloonCapacity(3), BalloonCapacity(2), BalloonCapacity(1)))
  }

  "isInflate" should "return Some(true) for INFLATE" in {
    sut.isInflate("INFLATE") shouldBe Some(true)
  }

  "isInflate" should "return Some(false) for BANK" in {
    sut.isInflate("BANK") shouldBe Some(false)
  }

  "isInflate" should "return None for anything else" in {
    sut.isInflate("anything else") shouldBe None
  }
}