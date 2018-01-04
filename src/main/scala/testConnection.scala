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
      * ��ʽһ
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
      * ��ʽ��
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
    * ����ƽ�̵����⣬��scala�������ں��������伯��֮���Ԫ�صݹ飬���Եõ��Ľ���Ǹ�ӵ�еѿ������Ľ����
    * �ڴ˳���������ƥ��Ĳ���ȷʵ����ν��������ʵ���ݿ��ܾͲ�������������ˡ�
    *
    * @param strategy ������Ҫת���Ĳ��Լ����磺(�Ա�|ȫ��)&(��|��)&(��|Ӯ|����)
    * @return ���ؿ��������򷽷�ƥ��Ĳ��Լ��� (�Ա�&ȫ��&��)|(�Ա�&ȫ��&��)|(�Ա�&ȫ��&��)|(�Ա�&ȫ��&Ӯ)
    *         |(�Ա�&ȫ��&����)|(�Ա�&��&��)|(�Ա�&��&��)|(�Ա�&��&Ӯ)|(�Ա�&��&����)|(�Ա�&��&��)
    *         |(�Ա�&��&Ӯ)|(�Ա�&��&����)|(�Ա�&��&Ӯ)|(�Ա�&��&����)|(�Ա�&Ӯ&����)|(ȫ��&��&��)
    *         |(ȫ��&��&��)|(ȫ��&��&Ӯ)|(ȫ��&��&����)|(ȫ��&��&��)|(ȫ��&��&Ӯ)|(ȫ��&��&����)
    *         |(ȫ��&��&Ӯ)|(ȫ��&��&����)|(ȫ��&Ӯ&����)|(��&��&��)|(��&��&Ӯ)|(��&��&����)
    *         |(��&��&Ӯ)|(��&��&����)|(��&Ӯ&����)|(��&��&Ӯ)|(��&��&����)|(��&Ӯ&����)|(��&Ӯ&����)
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
