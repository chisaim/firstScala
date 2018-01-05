object Testswap1 extends App {

  def swap(array: Array[Int]): Array[Int] = {
    array match {
      case Array(a, b, rest@_*) => Array(b,a) ++ rest
      case _ => array
    }
  }

  println(swap(Array(2, 3, 4, 5)).mkString(","))
}
