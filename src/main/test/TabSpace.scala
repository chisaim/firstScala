import java.io.PrintWriter

import scala.io.Source

object TabSpace extends App {

  val Filename = "TabSpace"

  val path = "src/main/resources/" + Filename + ".txt"

  val lineIterator = Source.fromFile(path).getLines()

  lazy val TabIterator = Source.fromFile(path).getLines()

  val lineRecord = lineIterator.toArray

  lazy val pw = new PrintWriter(path)

  println(Filename+"文件内容如下:")

  lineRecord.foreach(println)
  lineRecord.foreach{
    line => pw.write(line.replaceAll("="," ")+ "\n")
  }

  pw.close()

  println("替换后"+Filename+"文件内容如下:")
  TabIterator.foreach(println)
}
