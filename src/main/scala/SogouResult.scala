
import java.sql.DriverManager

import entity.Converter

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.control.Breaks

object SogouResult {

  def main(args: Array[String]): Unit = {

    //        val messages = Source.fromFile("src/main/resources/messagedb.txt")

    //    val samples = Source.fromFile("src/main/resources/sampledb.txt")

    val url = "jdbc:mysql://192.168.102.117:3306/site?characterEncoding=utf8&allowMultiQueries=true&useSSL=false"

    val username = "chisaim"

    val password = "chisaim"

    Class.forName("com.mysql.jdbc.Driver").newInstance()

    val connection = DriverManager.getConnection(url, username, password)

    val pst = connection.prepareStatement("select messagedetail from site.example")
    val pst2 = connection.prepareStatement("select keyword from site.example")
    val rs = pst.executeQuery()
    val rs2 = pst2.executeQuery()
    val converter = new Converter()

    var hitCount = 0
    var missCount = 0

    val messageContent = new ArrayBuffer[String]()
    val sampleContent = new ArrayBuffer[String]()
    val hitArray = new ArrayBuffer[Int]()
    val missArray = new ArrayBuffer[Int]()
    val hitArrayContent = new ArrayBuffer[Int]()
    val missArrayContent = new ArrayBuffer[Int]()
    val arraycontent = new ArrayBuffer[Int]()


    while (rs.next()) {
      messageContent.+=(rs.getString("messagedetail"))
    }
    while (rs2.next()) {
      sampleContent.+=(rs2.getString("keyword"))
    }

    //    val messIterator = messages.getLines()

    //    val samIterator = samples.getLines()


    //    while (samIterator.hasNext) {
    //      sampleContent.+=(samIterator.next())
    //    }
    //    while (messIterator.hasNext) {
    //      messageContent.+=(messIterator.next())
    //    }

    val innerLoopContent = new Breaks
    val outerLoopContent = new Breaks

    outerLoopContent.breakable {
      for (regex <- sampleContent) {
        val pattern = StrategyTile(regex.replace("(", "").replace(")", "")).r()
        innerLoopContent.breakable {
          for (text <- messageContent) {
            if ((pattern findAllIn text).nonEmpty) {
              hitCount = 1
              hitArray.+=(hitCount)
            } else {
              missCount = 0
              missArray.+=(missCount)
            }
          }
          hitArrayContent += (hitArray.size)
          missArrayContent += (missArray.size)

          println(hitArray.size)
          println(missArray.size)
          hitArray.clear()
          missArray.clear()
        }
      }
    }
    println(hitArrayContent)
    println(missArrayContent)


    //    messages.close()
    //    samples.close()
  }

  def StrategyTile(strategy: String): String = {

    val iterator = strategy.replace("(", "").replace(")", "").split("&").toList
    val x = iterator.flatMap(_.replace("|", "=").split("=").toList).combinations(iterator.length)
    val sampleContent = new StringBuffer()
    x.foreach(y => {
      //      println(y.reduce(_ + "&" + _))
      sampleContent.append("(" + y.reduce(_ + "&" + _) + ")|")
    })

    return sampleContent.toString.substring(0, sampleContent.length() - 1)
  }
}
