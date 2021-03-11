case class ElevatorState(floor: Int = 0, goalFloors: List[Int] = List.empty) {
  def standingStill: Boolean = goalFloors.isEmpty

  def isGoingUp: Boolean = !standingStill && floor < goalFloors.min

  def isGoingDown: Boolean = !standingStill && floor > goalFloors.min

  def isMoving: Boolean = !standingStill
}