import java.io.PrintWriter

object Index extends App {

  val Filename = "Index"

  val path = "src/main/resources/" + Filename + ".txt"

  val out = new PrintWriter(path)

  for (i <- 0 to 20) {
    out.println(index(i))
  }

  def index(n: Int) = {
    val value = math.pow(2, n)

    "" * 4 + value.toInt + "" * (11 - value.toString().size) + math.pow(2, -n)
  }
}
