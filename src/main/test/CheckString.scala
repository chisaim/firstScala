import scala.io.Source

object CheckString extends App {

  val Filename = "checkintString"

  val path = "src/main/resources/" + Filename + ".txt"

  println(Filename+"�ļ��г��ȴ���12���ַ���Ϊ:")

  Source.fromFile(path).mkString.split("\\s+").foreach(str => if(str.length>12) println(str))

}
