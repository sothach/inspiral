package helpers

import inspiral.app.ConsoleInOut

class MockInputOutput(commands: Seq[String]) extends ConsoleInOut {
  var output: Seq[String] = Seq()
  var input: Seq[String] = commands

  override def readLine(): String = {
    val result = input.head
    input = input.drop(1)
    result
  }

  override def print(s: String) = {
    output = output :+ s
  }

  override def println(s: String) = this.print(s"$s")
}