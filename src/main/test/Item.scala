
abstract class Item {
  def price: Double

  def description: String
}

class SimpleItem(override val price: Double, override val description: String) extends Item {

}

class Bundle() extends Item {

  val itemList = scala.collection.mutable.ArrayBuffer[Item]()

  def addItem(item: Item): Unit = {
    itemList += item
  }

  override def price: Double = {
    var p: Double = 0
    itemList.foreach(i => p = p + i.price)
    p

  }

  override def description: String = {

    var des = ""

    itemList.foreach(i => des = des + i.description + "")
    des
  }

  object ItemTest {
    val bundle = new Bundle

    def main(args: Array[String]): Unit = {
      val priceArr=Array(2.5,100,3.5,40,32.5)
      val desArr=Array("铅笔","水杯","笔记本","火腿肠","鼠标")

      for(i <- 0 until 5){
        bundle.addItem(new SimpleItem(priceArr(i),desArr(i)))
        println("购物篮信息如下:")
        bundle.itemList.foreach(item=>println("描述: "+item.description+"价格: "+item.price))
        println("所购物品如下: "+bundle.description)
        println("本次购物合计: "+bundle.price+"￥")

      }
    }

  }

}