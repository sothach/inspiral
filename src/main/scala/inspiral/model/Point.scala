package inspiral.model

case class Point(x: Int, y: Int) {
  require(x > 0, s"x must be positive ($x)")
  require(y > 0, s"y must be positive ($y)")
  def distance(other: Point) = Math.abs(other.x-x) + Math.abs(other.y-y)
}