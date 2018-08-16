package inspiral.app

trait ConsoleInOut {
  def readLine(): String
  def print(line: String): Unit
  def println(line: String): Unit
}
