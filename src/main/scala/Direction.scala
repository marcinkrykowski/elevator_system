sealed trait Direction

case object Up extends Direction

case object Down extends Direction

object Direction {
  implicit def direction2Int(d: Direction): Int = d match {
    case Down => -1
    case Up => 1
  }
}

