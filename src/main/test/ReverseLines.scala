import java.io.PrintWriter

import scala.io.Source

object ReverseLines extends App {

  val filename = "File.txt"

  val Refilename = "ReverseFile.txt"

  val source = Source.fromFile("src/main/resources/" + filename)
  lazy val ReSource = Source.fromFile("src/main/resources/" + Refilename)
  lazy val pw = new PrintWriter("src/main/resources/" + Refilename)

  val lineIterator = source.getLines()

  val lineRecord = lineIterator.toArray

  val reverseRecord = lineRecord.reverse

  reverseRecord.foreach(
    line => pw.write(line+"\n")
  )

  pw.close()
  println(filename+"文件内容如下:")
  lineRecord.foreach(line=>println(line))

  println(Refilename+"文件内容如下:")
  ReSource.getLines().foreach(line=>println(line))

  println(source.mkString)
}
