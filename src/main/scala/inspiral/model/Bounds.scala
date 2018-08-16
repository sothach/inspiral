package inspiral.model

case class Bounds(lower: Point, upper: Point) {
  require(lower.x <= upper.x && lower.y <= upper.y, s"lower bound ($lower) must not be greater than upper ($upper)")
  def contains(point: Point) = point.x <= upper.x && point.y <= upper.y
}