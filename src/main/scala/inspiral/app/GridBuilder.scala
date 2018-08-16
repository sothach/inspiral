package inspiral.app

import inspiral.model._

import scala.annotation.tailrec

class GridBuilder(dim: Int, turtleMaker: Int => Turtle) {
  require(dim % 2 != 0, "grid must be odd-dimensioned")
  require(1 <= dim && dim <= 999, "grid dimensioned within limits (1 .. 999)")
  val max = scala.math.pow(dim, 2).toInt
  lazy val origin = this.locationOf(1)

  val pointMap = {
    @tailrec
    def buildMap(series: List[Int], point: Turtle, result: Map[Int,Point]): Map[Int,Point] = {
      series match {
        case Nil =>
          result
        case v :: tail =>
          buildMap(tail, point.walk, result + (v -> point.cursor))
      }
    }
    buildMap((1 to max).reverse.toList, turtleMaker(dim), Map[Int,Point]())
  }

  def show(console: ConsoleInOut) = {
    val width = (Math.log10(max) + 1).round
    val printRow = (data: Array[Int]) => {
      data foreach (v => console.print(v.formatted(s"%${width}d ")))
      console.println("")
    }
    gridView foreach printRow
  }

  def locationOf(i: Int) = {
    require(pointMap.contains(i), s"value ($i) must be within matrix series")
    pointMap(i)
  }

  val gridView = {
    val result = Array.tabulate(dim, dim)((_, _) => 0)
    pointMap foreach { case (item, pos) =>
      result(pos.x-1)(pos.y-1) = item
    }
    result
  }
}