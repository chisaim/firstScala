
object TestFunc {


  def main(args: Array[String]): Unit = {
    val str: String = "(ÌÔ±¦|È«³¡)&(Âú|Ïí)&(ÕÛ|Ó®|°üÓÊ)"

    val iterator = str.replace("(", "").replace(")", "").split("&").toList
    val x = iterator.flatMap(_.replace("|", "=").split("=").toList).combinations(iterator.length)
    val sampleContent = new StringBuffer()
    x.foreach(y => {
      println(y.reduce(_ + "&" + _))
      sampleContent.append("(" + y.reduce(_ + "&" + _) + ")|")
    })
    println(sampleContent.toString.substring(0, sampleContent.length() - 1))
  }
}