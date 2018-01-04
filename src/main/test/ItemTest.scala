
abstract class Item
case class Article(description: String, price: Double) extends Item
case class Bundle(description: String, discount: Double, items: Item*) extends Item
case class Multiple(count: Int, item: Item) extends Item

object ItemTest extends App {

  def price(item: Item) : Double ={
    item match {
      case Article(_,p) => p
      case Bundle(_,disc,its@_*) => its.map(price _).sum - disc
      case Multiple(count,it) => count * price(it)
    }
  }
  println(price(Multiple(10, Article("Blackwell Toster", 29.95))))

}
