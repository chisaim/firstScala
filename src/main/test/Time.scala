class Time(val hours: Int, val minutes: Int) {

  require(hours != 0,"this is not zero")
  def before(other: Time): Boolean = {
    hours < other.hours || (hours == other.hours && minutes < other.minutes)

  }

  override def toString: String = hours + ":" + minutes

}

object Time {
  def main(args: Array[String]): Unit = {
    val t1 = new Time(10, 30)
    val t2 = new Time(10, 50)
    val t3 = new Time(11, 10)

    println("t1��ʱ�˿���:" + t1.toString)
    println("t2��ʱ�˿���:" + t2.toString)
    println("t3��ʱ�˿���:" + t3.toString)
    println("t1ʱ������t2��" + t1.before(t2))
    println("t3ʱ������t2��" + t3.before(t2))
  }
}