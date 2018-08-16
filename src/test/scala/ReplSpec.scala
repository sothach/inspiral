import helpers.MockInputOutput
import inspiral.app.{InspiralRepl, _}
import org.scalatest.{FlatSpec, Matchers}

class ReplSpec extends FlatSpec with Matchers {

  "The REPL interface" should "work as expected when given valid inputs" in {
    val commands = Seq("5","12","q")
    val expected = Seq(
      "enter N (odd) dimension within Range 1 to 999: ",
      "17 ", "16 ", "15 ", "14 ", "13 ", "",
      "18 ", " 5 ", " 4 ", " 3 ", "12 ", "",
      "19 ", " 6 ", " 1 ", " 2 ", "11 ", "",
      "20 ", " 7 ", " 8 ", " 9 ", "10 ", "",
      "21 ", "22 ", "23 ", "24 ", "25 ", "",
      "Please enter number within Range 1 to 25: ",
      "1 @ Point(3,3) -> 12 @ Point(2,5) distance = 3",
      "Please enter number within Range 1 to 25: ")

    val io = new MockInputOutput(commands)
    new InspiralRepl(io).run()

    io.output shouldBe expected
  }

  "When an invalid grid dimension is entered, the Repl" should "display an error, and re-prompt" in {
    val commands = Seq("0","q")
    val expected = Seq(
      "enter N (odd) dimension within Range 1 to 999: ",
      "input error: [0]",
      "enter N (odd) dimension within Range 1 to 999: "
    )
    val io = new MockInputOutput(commands)
    new InspiralRepl(io).run()

    io.output shouldBe expected
  }

  "When the 'restart' command is entered, the Repl" should "request a new dimension" in {
    val commands = Seq("3","r","q")
    val expected = Seq(
      "enter N (odd) dimension within Range 1 to 999: ",
      " 5 ", " 4 ", " 3 ", "",
      " 6 ", " 1 ", " 2 ", "",
      " 7 ", " 8 ", " 9 ", "",
      "Please enter number within Range 1 to 9: ",
      "enter N (odd) dimension within Range 1 to 999: "
    )
    val io = new MockInputOutput(commands)
    new InspiralRepl(io).run()

    io.output shouldBe expected
  }

  "When the 'help' command is entered, the Repl" should "display the context's help" in {
    val commands = Seq("3","?","q")
    val expected = Seq(
      "enter N (odd) dimension within Range 1 to 999: ",
      " 5 ", " 4 ", " 3 ", "",
      " 6 ", " 1 ", " 2 ", "",
      " 7 ", " 8 ", " 9 ", "",
      "Please enter number within Range 1 to 9: ",
      "q:	exit",
      "?:	display this help",
      "s:	show grid",
      "r:	re-size grid",
      "Please enter number within Range 1 to 9: "
    )
    val io = new MockInputOutput(commands)
    new InspiralRepl(io).run()

    io.output shouldBe expected
  }

  "When the 'showGrid' command is entered, the Repl" should "redisplay the grid" in {
    val commands = Seq("3","s","q")
    val expected = Seq(
      "enter N (odd) dimension within Range 1 to 999: ",
      " 5 ", " 4 ", " 3 ", "",
      " 6 ", " 1 ", " 2 ", "",
      " 7 ", " 8 ", " 9 ", "",
      "Please enter number within Range 1 to 9: ",
      " 5 ", " 4 ", " 3 ", "",
      " 6 ", " 1 ", " 2 ", "",
      " 7 ", " 8 ", " 9 ", "",
      "Please enter number within Range 1 to 9: "
    )
    val io = new MockInputOutput(commands)
    new InspiralRepl(io).run()

    io.output shouldBe expected
  }

  "When an invalid command is entered, the Repl" should "display an error" in {
    val commands = Seq("3","x","q")
    val expected = Seq(
      "enter N (odd) dimension within Range 1 to 999: ",
      " 5 ", " 4 ", " 3 ", "",
      " 6 ", " 1 ", " 2 ", "",
      " 7 ", " 8 ", " 9 ", "",
      "Please enter number within Range 1 to 9: ",
      "input error: [x]: enter s, r, q or cell value",
      "Please enter number within Range 1 to 9: "
    )
    val io = new MockInputOutput(commands)
    new InspiralRepl(io).run()

    io.output shouldBe expected
  }

  "When the Repl is launched, the Std IO" should "be connected" in {
    import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

    val commands = "0\nq\n".getBytes
    val expected = """enter N (odd) dimension within Range 1 to 999: input error: [0]
                     |enter N (odd) dimension within Range 1 to 999: """.stripMargin
    System.setIn(new ByteArrayInputStream(commands))
    val out = new ByteArrayOutputStream
    System.setOut(new PrintStream(out))

    StdInspiralRepl.main(Array.empty)

    out.toString shouldBe expected
  }

}
