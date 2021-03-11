import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers;

class ElevatorSystemSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {

  "An ElevatorSystem" should "handle up to 16 elevators" in {
    MyElevatorSystem(elevatorsNum = 2).elevatorsNum should be(2)
    MyElevatorSystem(elevatorsNum = 16).elevatorsNum should be(16)

    an[IllegalArgumentException] should be thrownBy MyElevatorSystem(elevatorsNum = 200)
  }

  it should "query the state of the elevators" in {
    val elevatorSystem = MyElevatorSystem(elevatorsNum = 2)
    elevatorSystem.status() should contain theSameElementsAs List((0, 0, List()), (1, 0, List()))
  }

  it should "receive a pickup request" in {
    val elevatorSystem = MyElevatorSystem(3)

    // all elevators are at ground floor
    elevatorSystem.elevatorsNum should be(3)

    // 2 people queued up at 1st floor want to go down
    elevatorSystem.pickup(requestedFloor = 1, direction = Down)

    // go over resources and pick the first fulfilling the request
    // in current impl it'd be the first elevator
    // no randomness or letting them to offer resources/themselves
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 0, List(1)), (1, 0, List()), (2, 0, List()))

    // Time steps/ticks = one floor at a time across all elevators
    elevatorSystem.step()

    // first elevator at 1st floor
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 1, List()), (1, 0, List()), (2, 0, List()))

    // take me from level 4 to 2
    elevatorSystem.pickup(requestedFloor = 4, direction = Down)

    elevatorSystem.status() should contain theSameElementsAs
      List((0, 1, List(4)), (1, 0, List()), (2, 0, List()))
    elevatorSystem.step()

    elevatorSystem.status() should contain theSameElementsAs
      List((0, 2, List(4)), (1, 0, List()), (2, 0, List()))
    elevatorSystem.step()
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 3, List(4)), (1, 0, List()), (2, 0, List()))
    elevatorSystem.step()
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List()), (1, 0, List()), (2, 0, List()))

    // Why do I have to update?!
    // Is update to tell an elevator where to go? If so, why do I have to pass the current level?
    // Or is this to tell what elevator is coming to handle a passenger at goalFloor?
    // Let's assume it's to tell where an elevator should go to
    elevatorSystem.update(0, 4, 2)

    // elevator #0 is going down from 4 to 2
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List(2)), (1, 0, List()), (2, 0, List()))

    // take me from level 5 down
    elevatorSystem.pickup(requestedFloor = 5, direction = Down)
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List(2, 5)), (1, 0, List()), (2, 0, List()))

    // take me from level 5 up
    elevatorSystem.pickup(requestedFloor = 1, direction = Up)
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List(2, 5)), (1, 0, List(1)), (2, 0, List()))
  }
}