package inspiral.app

import java.io.{BufferedReader, InputStreamReader, PrintWriter}

class StdInputOutput extends ConsoleInOut {
  val stdin = new BufferedReader(new InputStreamReader(System.in))
  val stdout = new PrintWriter(System.out)
  stdout.flush()
  def readLine(): String = stdin.readLine
  def print(line: String): Unit = {
    stdout.print(line)
    stdout.flush()
  }
  def println(line: String): Unit = {
    stdout.println(line)
    stdout.flush()
  }
}