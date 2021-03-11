import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CostSpec extends AnyFlatSpec with Matchers {

  "Elevator" should "calculate its moving cost" in {
    val e = Elevator(0)
    e.movingCost(floor = 4, direction = Up) shouldBe 4

    e.move(floor = 3, direction = Up)

    e.update(floor = 2, goalFloor = 2)

    e.movingCost(floor = 4, direction = Up) shouldBe 2

    e.update(floor = 4, goalFloor = 4)

    e.move(floor = 3, direction = Up)

    e.update(floor = 3, goalFloor = 3)

    e.movingCost(floor = -4, direction = Down) shouldBe 7
  }
}