trait ElevatorSystem {
  def pickup(floor: Int, direction: Int)

  def update(elevatorId: Int, floor: Int, goalFloor: Int)

  def step()

  def status(): List[(Int, Int, List[Int])]
}