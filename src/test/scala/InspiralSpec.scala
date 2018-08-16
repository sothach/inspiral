import inspiral.model.Point
import inspiral.app.{SpiralTurtle, _}
import org.scalatest.{FlatSpec, Matchers}

class InspiralSpec extends FlatSpec with Matchers {

  "when the GridBuilder is asked to create a valid matrix, it" should
    "return the expected matrix values" in {

    val subject = new GridBuilder(5, dim => new SpiralTurtle(dim))
    val result = subject.gridView

    result.length shouldBe 5
    result.forall(row => row.length == 5) shouldBe true

    result(0).toSeq shouldBe Seq(17, 16, 15, 14, 13)
    result(1).toSeq shouldBe Seq(18,  5,  4,  3, 12)
    result(2).toSeq shouldBe Seq(19,  6,  1,  2, 11)
    result(3).toSeq shouldBe Seq(20,  7,  8,  9, 10)
    result(4).toSeq shouldBe Seq(21, 22, 23, 24, 25)

    subject.locationOf(1) shouldBe Point(3,3)
    subject.locationOf(25) shouldBe Point(5,5)
  }

  "a matrix containing the value 368078" should
    "have a distance of 31 between it and the cell 1" in {
    val subject = new GridBuilder(607,dim => new SpiralTurtle(dim))
    val origin = subject.locationOf(1)
    origin.distance(subject.locationOf(368078)) shouldBe 371
  }

  "if the distance between, it" should "fail" in {
    the [IllegalArgumentException] thrownBy {
      val grid = new GridBuilder(3, dim => new SpiralTurtle(dim))
      grid.locationOf(10)
    } should have message "requirement failed: value (10) must be within matrix series"
  }

  "when the GridBuilder is asked to create a matrix with an even number, it" should "fail" in {
    the [IllegalArgumentException] thrownBy {
      new GridBuilder(6, dim => new SpiralTurtle(dim))
    } should have message "requirement failed: grid must be odd-dimensioned"
  }

  "when the GridBuilder is asked to create a matrix with a too-large size it" should "fail" in {
    the [IllegalArgumentException] thrownBy {
      new GridBuilder(1001, dim => new SpiralTurtle(dim))
    } should have message "requirement failed: grid dimensioned within limits (1 .. 999)"
  }

}
