case class MyElevatorSystem(elevatorsNum: Int = 5) extends ElevatorSystem {

  require(elevatorsNum >= 0 && elevatorsNum <= 16,
    "Elevator system supports up to 16 elevators")

  val elevators: List[Elevator] = (0 until elevatorsNum).toList.map(n => Elevator(n))

  override def status(): List[(Int, Int, List[Int])] = {
    elevators.map(_.toStatus)
  }

  override def update(elevatorId: Int, currentFloor: Int, goalFloor: Int): Unit =
    elevators.find(e => e.id == elevatorId).foreach { e =>
      e.update(currentFloor, goalFloor)
    }


  override def pickup(requestedFloor: Int, direction: Int): Unit =
    elevators.minBy(_.movingCost(requestedFloor, direction)).move(requestedFloor, direction)


  override def step(): Unit = elevators.foreach(_.step)
}