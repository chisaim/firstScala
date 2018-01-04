import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.SparkContext

object OptRDDTest {

  def main(args: Array[String]): Unit = {
    //
    //    val nums = List(1, 4, 5, 3, 2, 6, 7, 9, 8, 10)
    //
    //    val text = List(
    //      "20111230000005,57375476989eea12893c0c3811607bcf,奇艺高清,1,1,http://www.qiyi.com/",
    //      "20111230000005,66c5bb7774e31d0a22278249b26bc83a,凡人修仙传,3,1,http://www.booksky.org/BookDetail.aspx?BookID=1050804&Level=1",
    //      "20111230000007,b97920521c78de70ac38e3713f524b50,本本联盟,1,1,http://www.bblianmeng.com/",
    //      "20111230000008,6961d0c97fe93701fc9c0d861d096cd9,华南师范大学图书馆,1,1,http://lib.scnu.edu.cn/",
    //      "20111230000008,f2f5a21c764aebde1e8afcc2871e086f,在线代理,2,1,http://proxyie.cn/",
    //      "20111230000009,96994a0480e7e1edcaef67b20d8816b7,伟大导演,1,1,http://movie.douban.com/review/1128960/",
    //      "20111230000009,698956eb07815439fe5f46e9a4503997,youku,1,1,http://www.youku.com/",
    //      "20111230000009,599cd26984f72ee68b2b6ebefccf6aed,安徽合肥365房产网,1,1,http://hf.house365.com/",
    //      "20111230000010,f577230df7b6c532837cd16ab731f874,哈萨克网址大全,1,1,http://www.kz321.com/",
    //      "20111230000010,285f88780dd0659f5fc8acc7cc4949f2,IQ数码,1,1,http://www.iqshuma.com/"
    //    )


    //    val nums = context.parallelize(1 to 10)
    //map操作是对列表中的每个元素进行变换应用的
    //    println(nums.map((x: Int) => x * x))
    //flattern是对列表进行平铺操作，flatmap是对列表的列表进行平铺的
    //    val textFlatMapped = text.flatMap(_.split(",").toList)
    //    println(textFlatMapped)
    //reduce是对列表内部每个元素之间进行变换应用的
    //    println(text.reduce(_ + "," + _))
    //    println(text.reduceLeft(_ + "," + _))
    //    println(text.reduceRight(_ + "," + _))
    //初始化一个括号中的值到集合中，left和right分别初始化到左边和右边
    //    println(nums.fold(1)(_+_))
    //    println(text.foldLeft("wwwwww")(_+_))
    //    println(text.foldRight("wwwwww")(_+_))
    //排序
    //    println(nums.sorted)
    //sortBy按照集合中内部的元素进行排序
    //    val users = List(("aaaaaa", 25), ("bbbbbbb", 23))
    //依照元组中的指定元素排序
    //        println(users.sortBy{case(user,age) => age})
    //        println(users.groupBy{case(user,age) => age})
    //将列表中的元素映射对象然后依照指定对象的值进行元素排序
    //        println(users.sortWith{case(user,age) => user._2 < user._2})
    //        println(users.groupBy{case(user,age) => age})

    //    val nums1 = List(1, 2, 3, 4, 4, 4, 4, 4, 4)
    //    val nums2 = List(2, 3, 4, 5)
    //    println(nums1 diff nums2)
    //    println(nums1 union nums2)
    //    println(nums1 intersect nums2)
    //    println(nums1.distinct)


    //    val data = List(("AAAAA", "men"), ("BBBB", "women"), ("CCCC", "men"), ("DDDD", "women"))
    //    println(data.groupBy(_._2))
    //    println(data.grouped(3).toList)


    //    println(nums.scan(0)(_+_))
    //    println(nums.scanLeft(0)(_+_))
    //    println(nums.scanRight(0)(_+_))

    //    println(nums.take(2))
    //    println(nums.takeRight(2))
    //    println(nums.takeWhile(_ == nums.head))

    //    println(nums.drop(1))
    //    println(nums.dropRight(1))
    //    println(nums.dropWhile(_ == nums.head))

    //    println(nums.span(_ == 1))
    //    println(nums.splitAt(3))
    //    println(nums.partition(_ % 3 == 0))
    //
    //    println(nums.padTo(20,"c"))

    //    val num1 = List(1, 2, 3, 4)
    //    println(nums.combinations(5).toList)
    //    println(num1.permutations.toList)

    //    println(nums.sorted zip text)

    //        val num1 = List(1, 2, 3, 4, 5, 6)
    //        val num2 = List(1, 2, 3, 4)
    //
    //        val text1 = List("A", "B", "C", "D", "E", "F")
    //        val text2 = List("A", "B", "C", "D")
    //
    //        println(num1 zipAll(text1, "*", -1))
    //        println(num2 zipAll(text1, "*", -1))
    //        println(num1 zipAll(text2, "*", -1))
    //        println((num1 zipAll(text2, "*", -1)).unzip)
    //        println((num1 zipAll(text2, "*", -1)).unzip3)

    //    println(nums.slice(3, 5))
    //    println(nums.sliding(2).toList)
    //    println(nums.sliding(2, 4).toList)
    //
    //    println(nums.updated(4,11))

    //        val conf = new SparkConf().setMaster("local[*]").setAppName("test")

    val context = new SparkContext("local", "mysql")

    val sampleRdd = new JdbcRDD(
      context,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://192.168.102.117:3306/site?characterEncoding=utf8&allowMultiQueries=true&useSSL=false", "chisaim", "chisaim")
      },
//      "SELECT keyword FROM site.example where id >= ? and id <= ?",
      "SELECT customer_name FROM site.customer where customer_id >= ? and customer_id <= ?",
      1001, 1028, 3, r => r.getString(1)

    ).cache()

//    print(rdd.filter(_.contains("success")).count())
    println(sampleRdd.collect().toList)

    val messageRdd = context.textFile("src/main/resources/messagedb.txt")
    val txt = messageRdd
    val txt1 = messageRdd
    txt.collect().take(10).foreach(println)


    context.stop()
  }


}
