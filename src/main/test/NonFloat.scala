import scala.io.Source

object NonFloat extends App {

  val source = Source.fromFile("src/main/resources/NumberFile.txt").mkString

  val pat1 = """[^((\d+\.){0,1}\d+)^\s+]+$""".r()

  val pat2 = """^((?!^[-]?\d*\.\d+$).)+$""".r()

  println("ģʽ1����������:")

  for(token <- source.split("\\s+")){
    for(word <- pat1.findAllIn(token)){
      if(!word.equals("")){
        println(token)
      }
    }
  }

  println("ģʽ2��������:")
  for(token <- source.split("\\s+")){
    for(word <- pat2.findAllIn(token)){
      println(word)
    }
  }

  /*
    for(token <- DataFrame){  strategyDataFrame
       val str1 = convter(token)
       for(line <- str1.findAllIn(){

    }
    */
}
