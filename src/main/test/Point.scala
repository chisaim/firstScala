class Point1(val x: Double, val y: Double) {
  override def toString: String = "x = " + x + "y = " + y
}

class LablePoint(val label: String, override val x: Double, override val y: Double) extends Point1(x, y) {
  override def toString: String = "label= " + label + "x= " + x + "y= " + y
}

object PointTest {

  def main(args: Array[String]): Unit = {
    val point = new Point1(2, 3)

    val lpoint = new LablePoint("т╡пн", 2, 3)

    println(point)
    println(lpoint)
  }
}