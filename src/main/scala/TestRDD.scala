import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object TestRDD {

  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setMaster("spark://192.168.102.75:7077").setAppName("rdd api test")
    val conf = new SparkConf().setMaster("local[*]").setAppName("rdd api test")
    val sc = SparkContext.getOrCreate(conf)
    mapTest(sc)
    //    distinctTest(sc)
    //    filterTest(sc)
    //    keyByTest(sc)
    //    sortByTest(sc)
    //    topNTest(sc)
    //    repartitionTest(sc)
    //    groupByTest(sc)
    //    aggSumTest(sc)
    sc.stop()
  }

  def mapTest(sc: SparkContext) = {
    //    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt",3)
    val file = sc.textFile("file:///D:\\IdeaResource\\firstScala\\src\\main\\resources\\messagedb.txt", 3)
    val mapResult = file.map(x => {
      //map的特点是一个输入对应一条输出，没有返回值，对应的返回值会是() NIL
      val info = x.split("\\t")
      (info(0), info(1)) //转换成了元组
    })
    //take是一个action，作用是取出前n条数据发送到driver，一般用于开发测试
    mapResult.take(10).foreach(println)

    //map和mapPartition的区别：map是一条记录一条记录的转换，mapPartition是
    //一个partition（分区）转换一次
    val mapPartitionResult = file.mapPartitions(x => {
      //一个分区对应一个分区
      var info = new Array[String](3)
      for (line <- x) yield {
        //yield：作用：有返回值，所有的记录返回之后是一个集合
        info = line.split("\\t")
        (info(0), info(1))
      }
    })
    mapPartitionResult.take(10).foreach(println)
    // 把一行转为多行记录，使用flatMap展平,把一条new_tweet记录转成两条login记录
    val flatMapTest = file.flatMap(x => {
      val info = x.split("\\t")
      info(1) match {
        case "new_tweet" => for (i <- 1 to 2) yield s"${info(0)} login ${info(2)}"
        case _ => Array(x)
      }
    })
    flatMapTest.take(10).foreach(println)
    println(file.count())
    println(flatMapTest.count())
  }

  //distinct：排重,把重复的数据去掉，不是数据的转换，属于数据的聚合
  def distinctTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt", 3)
    val userRdd = file.map(x => x.split("\\t")(0)).distinct()
    userRdd.foreach(println)
  }

  //filter:过滤
  def filterTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt", 3)
    val loginFilter = file.filter(x => x.split("\\t")(1) == "login")
    loginFilter.take(10).foreach(println)
    println(loginFilter.count())
  }

  //keyBy,输入作为value，key由算计计算而来
  def keyByTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt", 3)
    val userActionType = file.keyBy(x => {
      val info = x.split("\\t")
      s"${info(0)}--${info(1)}"
    })
    userActionType.take(10).foreach(println)
  }

  //sortBy排序
  def sortByTest(sc: SparkContext) = {
    val file = sc.textFile("file:///C:\\Users\\zuizui\\Desktop\\README.txt")
    //数据量小的话，想进行群排序，吧numPartitions设置成1
    //默认为圣墟，姜旭吧第二个参数设置为false
    //    val sortBy = file.sortBy(x=>x.split("\\s+")(1).toInt,numPartitions = 1)//后面有不同数量的空格时，使用\\s+来split
    val sortBy = file.sortBy(x => x.split("\\s+")(1).toInt, false, numPartitions = 1) //后面有不同数量的空格时，使用\\s+来split
    sortBy.foreach(println)
  }

  def topNTest(sc: SparkContext) = {
    val list = List(1, 23, 34, 54, 56, 100)
    //把集合转化为RDD使用parallelize，或者mkRDD
    val rdd = sc.parallelize(list, 2)
    //添加饮食准换，使takeOrdered，和top的排序顺序变反
    implicit val tonordered = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = y.compareTo(x)
    }
    val takeOrdered = rdd.takeOrdered(3) //从小到大取出前三条
    takeOrdered.foreach(println)
    val topN = rdd.top(3) //从大到小取出前三条
    topN.foreach(println)
  }

  //重新分区
  def repartitionTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt")
    val result = file.repartition(5) //repartition是宽依赖，所谓宽依赖就是
    //原来RDD的每一个分区中的数据都会分别吧部分数据写入到新的RDD的每个分区中
    //窄依赖：就是原来RDD的分区中的一个分区数据完全写入到新的RDD中的一个分区中
    //窄依赖减少网络间的传输
    file.foreachPartition(x => {
      var sum = 0
      x.foreach(x => sum += 1)
      println(s"该分区的数据有${sum}")
    })

    result.foreachPartition(x => {
      var sum = 0
      x.foreach(x => sum += 1)
      println(s"该分区的数据有${sum}")
    })

    val coalesce = result.coalesce(3) //使用窄依赖，原来有五个分区，现在变成三个的话，
    //其中的一个不变，另外四个分区中的两两分别通过窄依赖添加到另外两个新的分区中
    coalesce.foreachPartition(x => {
      var sum = 0
      x.foreach(x => sum += 1)
      println(s"coalesce该分区的数据有${sum}")
    })
  }

  def groupByTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt")
    val groupedBy = file.groupBy(x => x.split("\\t")(0))
    //group by 容易发生数倾斜
    groupedBy.foreachPartition(x => {
      println(s"groupByRDD分区，该分区共有：${x.size}条记录")
    })
    groupedBy.foreach(x => {
      println(s"groupByRDD的一条记录，key为${x._1},value上集合记录条数是：${x._2.size}")
    })
    groupedBy.foreach(x => {
      var sum = 0
      x._2.foreach(line => {
        line.split("\\t")(1) match {
          case "login" => sum += 1
          case _ =>
        }
      })
      println(s"用户：${x._1}的登录次数是：$sum")
    })
  }

  def aggSumTest(sc: SparkContext) = {
    val list = List(1, 2, 4, 5)
    val rdd = sc.parallelize(list, 3)
    //reduce 计算sum
    val reduceResult = rdd.reduce((v1, v2) => v1 + v2)
    //fold计算sum
    val flodResult = rdd.fold(0)((v1, v2) => v1 + v2)
    //aggregate把元素连接成一个字符串
    val aggResult = rdd.aggregate("")((c, v) => {
      c match {
        case "" => v.toString
        case _ => s"$c,$v"
      }
    }, (c1, c2) => {
      c1 match {
        case "" => c2
        case _ => s"$c1,$c2"
      }
    })

    println(s"reduceResult:$reduceResult")
    println(s"flodResult:$flodResult")
    println(s"aggResult:$aggResult")
  }

  def persistTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt")
    //    file.cache()
    file.persist(StorageLevel.MEMORY_ONLY) //相当于cache()，智加载在内存中
    //计算用户数量
    //计算ip数量
    //计算每个用户在每一个ip上的数量
  }
}
