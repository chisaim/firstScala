import scala.io.Source

object Regex extends App {

  val Filename = "Regex"

  val path = "src/main/resources/" + Filename + ".txt"

  val pat1=""""like this，maybe with \\" or\\{2}"""".r
  val pat2="""like this，maybe with \\" or\\{2}""".r
  val pat3="""\w+\s+\\"""".r

  val linesIterator1=Source.fromFile(path).getLines()
  val linesIterator2=Source.fromFile(path).getLines()
  val linesIterator3=Source.fromFile(path).getLines()
  linesIterator1.foreach(line=>pat1.findAllIn(line).foreach (println))
  println("文本中包含:"+"""like this，maybe with \" or\\""")
  linesIterator2.foreach(line=>pat2.findAllIn(line).foreach (println))
  println("文本中包含:"+"\\w+\\s+\"")
  linesIterator3.foreach(line=>pat3.findAllIn(line).foreach(println))


}
