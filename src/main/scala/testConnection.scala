import java.util.Properties

import org.apache.spark.sql._

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks
import scala.util.matching.Regex


object testConnection {

  val url = "jdbc:mysql://192.168.102.117:3306/site?characterEncoding=utf8&allowMultiQueries=true&useSSL=false"

  val username = "chisaim"

  val password = "chisaim"

  def main(args: Array[String]): Unit = {

    val context = SparkSession.builder().master("local").appName("SparkSqlTest").config("spark.sql.shuffle.partitions", "2").getOrCreate()

    /**
      * 方式一
      */
    //    val reader = context.read.format("jdbc")
    //    reader.option("url", url)
    //    reader.option("driver", "com.mysql.jdbc.Driver")
    //    reader.option("user", username)
    //    reader.option("password", password)
    //    reader.option("dbtable", "example")
    //    reader.option("dbtable", "strategyTable")
    //    reader.load().createTempView("strategyTable")
    /**
      * 方式二
      */
    val prop = new Properties()
    prop.setProperty("user", "chisaim")
    prop.setProperty("password", "chisaim")

    val dataFrame = context.read.jdbc(url, "strategyTable", prop)

    val dataFrame2 = context.read.jdbc(url, "strategyTableEx", prop)
    dataFrame2.createTempView("temp_strategyTable")

    val loop1 = new Breaks
    val loop2 = new Breaks

    loop1.breakable {

      dataFrame.select("Strategies").foreach(column => {

        loop2.breakable {

          dataFrame2.select("Strategies").foreach(column2 => {

            //            Result(StrategyTile(column.getString(0)).r(), column2.getString(0))

            println("xxxxx")
          })

        }

      })

    }


    dataFrame2.createTempView("temp_strategyTableEx")

    //    context.sql("select * from temp_strategyTable").write.mode(SaveMode.Append).jdbc(url, "strategyTableEx", prop)
    dataFrame.foreach(column => {

      //      if ((StrategyTile(column.getString(0)).r() findAllIn column.getString(1)).nonEmpty) {}
      //      Result(StrategyTile(column.getString(0)).r(),dataFrame)
    })
    context.stop()
  }

  val hitlists = new ArrayBuffer[Int]()
  val misslists = new ArrayBuffer[Int]()
  val hitArrayContent = new ArrayBuffer[Int]()
  val missArrayContent = new ArrayBuffer[Int]()
  var count = 0
  val loop1 = new Breaks

  def Result(regex: Regex, sample: String): Int = {

    if ((regex findAllIn sample).nonEmpty) {
      count = 1
      hitlists.+=(count)
    } else {
      count = 0
      misslists.+=(count)
    }
    hitArrayContent.+=(hitlists.size)
    missArrayContent.+=(misslists.size)
    hitlists.clear()
    misslists.clear()

    println(hitArrayContent)
    println(missArrayContent)


    return 0
  }


  /**
    * 策略平铺的问题，用scala语言是在很难做到夸集合之间的元素递归，所以得到的结果是个拥有笛卡尔积的结果。
    * 在此程序中用来匹配的策略确实无所谓，但是真实数据可能就不能用这个方法了。
    *
    * @param strategy 输入需要转换的策略集合如：(淘宝|全场)&(满|享)&(折|赢|包邮)
    * @return 返回可以用正则方法匹配的策略集合 (淘宝&全场&满)|(淘宝&全场&享)|(淘宝&全场&折)|(淘宝&全场&赢)
    *         |(淘宝&全场&包邮)|(淘宝&满&享)|(淘宝&满&折)|(淘宝&满&赢)|(淘宝&满&包邮)|(淘宝&享&折)
    *         |(淘宝&享&赢)|(淘宝&享&包邮)|(淘宝&折&赢)|(淘宝&折&包邮)|(淘宝&赢&包邮)|(全场&满&享)
    *         |(全场&满&折)|(全场&满&赢)|(全场&满&包邮)|(全场&享&折)|(全场&享&赢)|(全场&享&包邮)
    *         |(全场&折&赢)|(全场&折&包邮)|(全场&赢&包邮)|(满&享&折)|(满&享&赢)|(满&享&包邮)
    *         |(满&折&赢)|(满&折&包邮)|(满&赢&包邮)|(享&折&赢)|(享&折&包邮)|(享&赢&包邮)|(折&赢&包邮)
    */
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
