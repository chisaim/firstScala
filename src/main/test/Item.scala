
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
      val desArr=Array("Ǧ��","ˮ��","�ʼǱ�","���ȳ�","���")

      for(i <- 0 until 5){
        bundle.addItem(new SimpleItem(priceArr(i),desArr(i)))
        println("��������Ϣ����:")
        bundle.itemList.foreach(item=>println("����: "+item.description+"�۸�: "+item.price))
        println("������Ʒ����: "+bundle.description)
        println("���ι���ϼ�: "+bundle.price+"��")

      }
    }

  }

}