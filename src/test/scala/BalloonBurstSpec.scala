import models.{BalloonCapacity, GameState, Points}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec._
import org.scalatest.matchers._
import utils.{IOHelper, ValidationHelper}

class BalloonBurstSpec extends AnyFlatSpec with should.Matchers with MockFactory {

  val mockIOHelper: IOHelper = mock[IOHelper]

  val mockValidationHelper: ValidationHelper = mock[ValidationHelper]

  val sut: BalloonBurst = new BalloonBurst(mockIOHelper, mockValidationHelper)

  val bankedPoints = Points(10)
  val unbankedPoints = Points(5)
  val currentBalloonCapacity = BalloonCapacity(0)

  //TODO - Verify method calls and fix Unexpected call errors
  "runGameIteration" should "print the score when the last balloon bursts" in {

    val otherBalloons = List.empty
    val oneBalloonLeft = GameState(bankedPoints, unbankedPoints, currentBalloonCapacity, otherBalloons)

    sut.runGameIteration(oneBalloonLeft)

    //Inflate the last balloon which has 0 capacity
    (mockIOHelper.readInput _).when().returning("INFLATE").noMoreThanOnce()
    (mockValidationHelper.isInflate _).when("INFLATE").returning(Some(true)).noMoreThanOnce()

    //Verify that there is a call to printBurst and printScore
  }

  "runGameIteration" should "print the score when the last balloon is banked" in {
    val otherBalloons = List.empty
    val oneBalloonLeft = GameState(bankedPoints, unbankedPoints, currentBalloonCapacity, otherBalloons)

    sut.runGameIteration(oneBalloonLeft)

    //Bank the last balloon
    (mockIOHelper.readInput _).when().returning("BANK").noMoreThanOnce()
    (mockValidationHelper.isInflate _).when("BANK").returning(Some(true)).noMoreThanOnce()

    //Verify that there is NO call to printBurst but there is to printScore
  }

  "runGameIteration" should "burst a balloon and run a new iteration" in {
    val otherBalloons = List(BalloonCapacity(1))
    val oneBalloonLeft = GameState(bankedPoints, unbankedPoints, currentBalloonCapacity, otherBalloons)

    sut.runGameIteration(oneBalloonLeft)

    //Inflate the second to last balloon which has 0 capacity
    (mockIOHelper.readInput _).when().returning("INFLATE").noMoreThanOnce()
    (mockValidationHelper.isInflate _).when("INFLATE").returning(Some(true)).noMoreThanOnce()

    //Verify that there is a call to printBurst
    //Verify there is a call to run an new iteration of the game
    // Where bankedPoints = 10, unbankedPoints = 0, the currentBalloonCapacity = 1 and otherBalloons is List.Empty
  }

  "runGameIteration" should "bank a score and run a new iteration" in {
    val otherBalloons = List(BalloonCapacity(1))
    val twoBalloonsLeft = GameState(bankedPoints, unbankedPoints, currentBalloonCapacity, otherBalloons)

    sut.runGameIteration(twoBalloonsLeft)

    //Bank the second to last balloon
    (mockIOHelper.readInput _).when().returning("BANK").noMoreThanOnce()
    (mockValidationHelper.isInflate _).when("BANK").returning(Some(true)).noMoreThanOnce()

    //Verify that there is NO call to printBurst
    //Verify there is a call to run an new iteration of the game
    // Where bankedPoints = 15, unbankedPoints = 0, the currentBalloonCapacity = 1 and otherBalloons is List.Empty
  }
}