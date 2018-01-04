import scala.io.Source

object CheckString extends App {

  val Filename = "checkintString"

  val path = "src/main/resources/" + Filename + ".txt"

  println(Filename+"文件中长度大于12的字符串为:")

  Source.fromFile(path).mkString.split("\\s+").foreach(str => if(str.length>12) println(str))

}
