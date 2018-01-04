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
      //map���ص���һ�������Ӧһ�������û�з���ֵ����Ӧ�ķ���ֵ����() NIL
      val info = x.split("\\t")
      (info(0), info(1)) //ת������Ԫ��
    })
    //take��һ��action��������ȡ��ǰn�����ݷ��͵�driver��һ�����ڿ�������
    mapResult.take(10).foreach(println)

    //map��mapPartition������map��һ����¼һ����¼��ת����mapPartition��
    //һ��partition��������ת��һ��
    val mapPartitionResult = file.mapPartitions(x => {
      //һ��������Ӧһ������
      var info = new Array[String](3)
      for (line <- x) yield {
        //yield�����ã��з���ֵ�����еļ�¼����֮����һ������
        info = line.split("\\t")
        (info(0), info(1))
      }
    })
    mapPartitionResult.take(10).foreach(println)
    // ��һ��תΪ���м�¼��ʹ��flatMapչƽ,��һ��new_tweet��¼ת������login��¼
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

  //distinct������,���ظ�������ȥ�����������ݵ�ת�����������ݵľۺ�
  def distinctTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt", 3)
    val userRdd = file.map(x => x.split("\\t")(0)).distinct()
    userRdd.foreach(println)
  }

  //filter:����
  def filterTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt", 3)
    val loginFilter = file.filter(x => x.split("\\t")(1) == "login")
    loginFilter.take(10).foreach(println)
    println(loginFilter.count())
  }

  //keyBy,������Ϊvalue��key����Ƽ������
  def keyByTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt", 3)
    val userActionType = file.keyBy(x => {
      val info = x.split("\\t")
      s"${info(0)}--${info(1)}"
    })
    userActionType.take(10).foreach(println)
  }

  //sortBy����
  def sortByTest(sc: SparkContext) = {
    val file = sc.textFile("file:///C:\\Users\\zuizui\\Desktop\\README.txt")
    //������С�Ļ��������Ⱥ���򣬰�numPartitions���ó�1
    //Ĭ��Ϊʥ�棬����ɵڶ�����������Ϊfalse
    //    val sortBy = file.sortBy(x=>x.split("\\s+")(1).toInt,numPartitions = 1)//�����в�ͬ�����Ŀո�ʱ��ʹ��\\s+��split
    val sortBy = file.sortBy(x => x.split("\\s+")(1).toInt, false, numPartitions = 1) //�����в�ͬ�����Ŀո�ʱ��ʹ��\\s+��split
    sortBy.foreach(println)
  }

  def topNTest(sc: SparkContext) = {
    val list = List(1, 23, 34, 54, 56, 100)
    //�Ѽ���ת��ΪRDDʹ��parallelize������mkRDD
    val rdd = sc.parallelize(list, 2)
    //�����ʳ׼����ʹtakeOrdered����top������˳��䷴
    implicit val tonordered = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = y.compareTo(x)
    }
    val takeOrdered = rdd.takeOrdered(3) //��С����ȡ��ǰ����
    takeOrdered.foreach(println)
    val topN = rdd.top(3) //�Ӵ�Сȡ��ǰ����
    topN.foreach(println)
  }

  //���·���
  def repartitionTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt")
    val result = file.repartition(5) //repartition�ǿ���������ν����������
    //ԭ��RDD��ÿһ�������е����ݶ���ֱ�ɲ�������д�뵽�µ�RDD��ÿ��������
    //խ����������ԭ��RDD�ķ����е�һ������������ȫд�뵽�µ�RDD�е�һ��������
    //խ�������������Ĵ���
    file.foreachPartition(x => {
      var sum = 0
      x.foreach(x => sum += 1)
      println(s"�÷�����������${sum}")
    })

    result.foreachPartition(x => {
      var sum = 0
      x.foreach(x => sum += 1)
      println(s"�÷�����������${sum}")
    })

    val coalesce = result.coalesce(3) //ʹ��խ������ԭ����������������ڱ�������Ļ���
    //���е�һ�����䣬�����ĸ������е������ֱ�ͨ��խ������ӵ����������µķ�����
    coalesce.foreachPartition(x => {
      var sum = 0
      x.foreach(x => sum += 1)
      println(s"coalesce�÷�����������${sum}")
    })
  }

  def groupByTest(sc: SparkContext) = {
    val file = sc.textFile("file:///G:\\bd14\\user-logs-large.txt")
    val groupedBy = file.groupBy(x => x.split("\\t")(0))
    //group by ���׷�������б
    groupedBy.foreachPartition(x => {
      println(s"groupByRDD�������÷������У�${x.size}����¼")
    })
    groupedBy.foreach(x => {
      println(s"groupByRDD��һ����¼��keyΪ${x._1},value�ϼ��ϼ�¼�����ǣ�${x._2.size}")
    })
    groupedBy.foreach(x => {
      var sum = 0
      x._2.foreach(line => {
        line.split("\\t")(1) match {
          case "login" => sum += 1
          case _ =>
        }
      })
      println(s"�û���${x._1}�ĵ�¼�����ǣ�$sum")
    })
  }

  def aggSumTest(sc: SparkContext) = {
    val list = List(1, 2, 4, 5)
    val rdd = sc.parallelize(list, 3)
    //reduce ����sum
    val reduceResult = rdd.reduce((v1, v2) => v1 + v2)
    //fold����sum
    val flodResult = rdd.fold(0)((v1, v2) => v1 + v2)
    //aggregate��Ԫ�����ӳ�һ���ַ���
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
    file.persist(StorageLevel.MEMORY_ONLY) //�൱��cache()���Ǽ������ڴ���
    //�����û�����
    //����ip����
    //����ÿ���û���ÿһ��ip�ϵ�����
  }
}
