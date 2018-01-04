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

    println("t1此时此刻是:" + t1.toString)
    println("t2此时此刻是:" + t2.toString)
    println("t3此时此刻是:" + t3.toString)
    println("t1时刻早于t2吗" + t1.before(t2))
    println("t3时刻早于t2吗" + t3.before(t2))
  }
}