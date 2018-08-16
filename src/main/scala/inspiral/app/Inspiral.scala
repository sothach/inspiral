package inspiral.app

trait State {
  def action(): State
}
object Finished extends State {
  def action() = this
}

trait InputState extends State {
  val exit = "q"
  val help = "?"
  def commandHelp: Seq[(String,String)] = Seq(
    ("q", "exit"),
    ("?", "display this help")
  )
  def console: ConsoleInOut
  def prompt: String
  def next(input: String): State
  def range: Range.Inclusive
  val isNumberInRange = (s: String) =>
      s.nonEmpty && (s forall Character.isDigit) &&
      range.contains(s.toInt) match {
    case true => Some(s.toInt)
    case false => None
  }
  def isValid(s: String): Boolean
  def action() = {
    console.print(prompt)
    console.readLine().trim.toLowerCase match {
      case v if v == exit =>
        Finished
      case v if v == help =>
        DisplayHelp(this)
      case v =>
        next(v)
    }
  }
  def onError(input: String) = console.println(s"input error: $input")
}
case class DisplayGrid(parent: MeasureDistance) extends State {
  def action() = {
    parent.grid.show(parent.console)
    parent.action()
  }
}
case class DisplayHelp(parent: InputState) extends State {
  def action() = {
    parent.commandHelp foreach { case (command, purpose) =>
      parent.console.println(s"$command:\t$purpose")
    }
    parent.action()
  }
}

case class NeedDimension(console: ConsoleInOut) extends InputState {
  def isValid (n: String) = isNumberInRange(n).exists(_ % 2 != 0)
  val range = 1 to 999
  val prompt = s"enter N (odd) dimension within $range: "

  def next(value: String): State = value match {
    case n if isValid(n) =>
      val grid = new GridBuilder(n.toInt, dim => new SpiralTurtle(dim))
      grid.show(console)
      MeasureDistance(console, grid)
    case error =>
      onError(s"[$error]")
      this
  }
}

case class MeasureDistance(console: ConsoleInOut, grid: GridBuilder) extends InputState {
  def isValid (n: String) = isNumberInRange(n).isDefined
  val range = 1 to grid.max
  val prompt = s"Please enter number within $range: "
  val showGrid = "s"
  val restart = "r"
  override val commandHelp = super.commandHelp ++ Seq(
    (showGrid, "show grid"),
    (restart, "re-size grid")
  )

  def next(value: String): State = {
    value match {
      case v if v == restart =>
        NeedDimension(console)
      case v if v == showGrid =>
        DisplayGrid(this)
      case n if isValid(n) =>
        val cell = grid.locationOf(n.toInt)
        val distance = grid.origin.rectilinearDistance(cell)
        console.println(s"1 @ ${grid.origin} -> $n @ $cell distance = $distance")
        this
      case error =>
        onError(s"[$error]: enter $showGrid, $restart, $exit or cell value")
        this
    }
  }
}

class InspiralRepl(console: ConsoleInOut) {
  import scala.annotation.tailrec

  @tailrec
  private def inputLoop(next: State): State = next match {
      case Finished => Finished
      case state => inputLoop(state.action())
    }

  def run() = inputLoop(NeedDimension(console))
}

object StdInspiralRepl extends InspiralRepl(new StdInputOutput) {
  def main(args: Array[String]): Unit = run()
}