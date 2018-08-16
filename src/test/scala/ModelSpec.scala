import inspiral.model.{Bounds, Point}
import org.scalatest.{FlatSpec, Matchers}

class ModelSpec extends FlatSpec with Matchers {

  "An attempt to construct a Point with an invalid 'x' coordinate" should "fail" in {
    the[IllegalArgumentException] thrownBy {
      Point(-1, 1)
    } should have message "requirement failed: x must be positive (-1)"
  }

  "An attempt to construct a Point with an invalid 'y' coordinate" should "fail" in {
    the[IllegalArgumentException] thrownBy {
      Point(1, -1)
    } should have message "requirement failed: y must be positive (-1)"
  }

  "An attempt to construct a Bounds with invalid points" should "fail" in {
    the[IllegalArgumentException] thrownBy {
      Bounds(Point(3,1), Point(2,2))
    } should have message "requirement failed: lower bound (Point(3,1)) must not be greater than upper (Point(2,2))"
  }

  "Point distance" should "be calculated correctly" in {
    val Bounds(lower, upper) = Bounds(Point(1,1), Point(3,3))
    lower.distance(upper) shouldBe 4
    lower.distance(lower) shouldBe 0
  }

  "Point unapply" should "work" in {
    val Point(x, y) = Point(1, 2)
    x shouldBe 1
    y shouldBe 2
  }

  "Bounds unapply" should "work" in {
    val Bounds(lower, upper) = Bounds(Point(1,1), Point(3,3))
    lower shouldBe Point(1,1)
    upper shouldBe Point(3,3)
  }

}
