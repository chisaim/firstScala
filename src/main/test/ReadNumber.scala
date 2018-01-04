import scala.io.Source

object ReadNumber extends App {

  val pattern = "(\\d+[.]\\d+)".r()

  val pattern1 = "^\\d+(\\.\\d+)?".r()

  val pattern2 = "[0-9]+(\\.\\d+)?".r()

  val FileName="NumberFile"

  val path = "src/main/resources/"+FileName+".txt"

  val FileStr = Source.fromFile(path).mkString

  val StrArray = pattern2.findAllIn(FileStr).toArray

  var total = 0d

  val len = StrArray.length

  StrArray.foreach(total += _.toDouble)

  println("�ı��и������ܺ�: "+total)
  println("�ı��и�����ƽ����: "+total/len+len)
  println("�ı��и����������ֵ: "+StrArray.max)
  println("�ı��и����������ֵ: "+StrArray.min)

}
