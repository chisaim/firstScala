import scala.io.Source

object WebSrc extends App {

  val pat = """<img.*?src=["'](.+?)["'].*?>""".r()

  val url = Source.fromURL("http://www.baidu.com").mkString

  for(pat(src) <- pat.findAllIn(url)){
    println(src)
  }
}
