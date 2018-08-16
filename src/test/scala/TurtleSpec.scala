import inspiral.model.{Bounds, Direction, Point}
import inspiral.app.SpiralTurtle
import org.scalatest.{FlatSpec, Matchers}

class TurtleSpec extends FlatSpec with Matchers {

  "An attempt to construct an invalid Turtle" should "fail the requirement" in {
    the[IllegalArgumentException] thrownBy {
      SpiralTurtle(Point(20,20), Bounds(Point(1,1),Point(10,10)), Direction.LEFT)
    } should have message "requirement failed: current point Point(20,20) must be within Bounds(Point(1,1),Point(10,10))"
  }

}
