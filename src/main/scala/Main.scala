object Main extends App{

  val system = MyElevatorSystem(2)

  system.pickup(3, Up)

  println(system.status())

}
