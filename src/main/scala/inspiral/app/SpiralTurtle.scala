package inspiral.app

import inspiral.model.Direction._
import inspiral.model.{Bounds, Point, Turtle}

case class SpiralTurtle(cursor: Point, bounds: Bounds, direction: Direction = LEFT) extends Turtle {
  require(bounds.contains(cursor), s"current point $cursor must be within $bounds")
  def this(dim: Int) = this(Point(dim, dim), Bounds(Point(1, 1), Point(dim, dim)))
  def walk = direction match {
    case LEFT if cursor == bounds.lower =>
      SpiralTurtle(Point(cursor.x,bounds.lower.y), bounds, UP)
    case LEFT if cursor.y == bounds.lower.y =>
      SpiralTurtle(Point(cursor.x - 1,bounds.lower.y), bounds, UP)
    case LEFT =>
      SpiralTurtle(Point(cursor.x, cursor.y-1), bounds, direction)
    case UP if cursor.x == bounds.lower.x =>
      SpiralTurtle(Point(bounds.lower.x,bounds.lower.y + 1),
        Bounds(Point(bounds.lower.x + 1, bounds.lower.y), bounds.upper), RIGHT)
    case UP =>
      SpiralTurtle(Point(cursor.x - 1, cursor.y), bounds, direction)
    case RIGHT if cursor.y == bounds.upper.y =>
      SpiralTurtle(Point(cursor.x + 1, bounds.upper.y),
        Bounds(Point(bounds.lower.x,bounds.lower.y + 1),
               Point(bounds.upper.x - 1,bounds.upper.y)), DOWN)
    case RIGHT =>
      SpiralTurtle(Point(cursor.x, cursor.y+1), bounds, direction)
    case DOWN if cursor.x == bounds.upper.x =>
      SpiralTurtle(Point(cursor.x, cursor.y-1),
        Bounds(bounds.lower, Point(bounds.upper.x,bounds.upper.y - 1)), LEFT)
    case DOWN =>
      SpiralTurtle(Point(cursor.x + 1, cursor.y), bounds, direction)
  }
}