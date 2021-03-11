case class Elevator(id: Int) {
  var _state: ElevatorState = ElevatorState()

  def update(floor: Int, goalFloor: Int): Unit =
    _state = ElevatorState(floor, List(goalFloor))

  def move(floor: Int, direction: Int): Unit =
    _state = _state.copy(goalFloors = _state.goalFloors :+ floor)


  def step: Unit = {
    val step = if (state.isGoingUp) {
      1
    } else if (state.isGoingDown) {
      -1
    } else {
      0
    }
    _state = _state.copy(floor = _state.floor + step)

    // remove visited floors
    _state = _state.copy(goalFloors = _state.goalFloors.filterNot(floor => floor == _state.floor))
  }


  def movingCost(floor: Int, direction: Int): Int = {
    math.abs(state.floor - floor)
  }

  def state: ElevatorState = _state

  def toStatus: (Int, Int, List[Int]) = (id, state.floor, state.goalFloors)
}