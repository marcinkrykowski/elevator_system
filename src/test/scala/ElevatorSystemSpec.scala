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

    // all elevators at ground floor
    elevatorSystem.elevatorsNum should be(3)

    // request at 1st floor to go down
    elevatorSystem.pickup(requestedFloor = 1, direction = Down)

    // go over resources and pick the first fulfilling the request=
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 0, List(1)), (1, 0, List()), (2, 0, List()))

    // elevator goes up as requested
    elevatorSystem.step()

    // first elevator at 1st floor
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 1, List()), (1, 0, List()), (2, 0, List()))

    // request at 4th floor to go down
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

    // update is used to tell where an elevator should go to
    elevatorSystem.update(0, 4, 2)

    // elevator id=0 is going down from 4 to 2
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List(2)), (1, 0, List()), (2, 0, List()))

    // take me from 5th floor down
    elevatorSystem.pickup(requestedFloor = 5, direction = Down)
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List(2, 5)), (1, 0, List()), (2, 0, List()))

    // take me from level 5 up
    elevatorSystem.pickup(requestedFloor = 1, direction = Up)
    elevatorSystem.status() should contain theSameElementsAs
      List((0, 4, List(2, 5)), (1, 0, List(1)), (2, 0, List()))

    elevatorSystem.step()
    elevatorSystem.step()
    elevatorSystem.step()
    elevatorSystem.step()
    elevatorSystem.step()

    elevatorSystem.status() should contain theSameElementsAs
      List((0, 5, List()), (1, 1, List()), (2, 0, List()))

    elevatorSystem.update(1, 1, 0)
    elevatorSystem.update(0, 5, 3)

    elevatorSystem.step()
    elevatorSystem.step()

    elevatorSystem.status() should contain theSameElementsAs
      List((0, 3, List()), (1, 0, List()), (2, 0, List()))
  }
}