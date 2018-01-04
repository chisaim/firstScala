class Car(
           val manufactor: String,
           val model:String = null,
           val year:String = null,
           var number:Int = -1
         )
object Car{
  def main(args: Array[String]): Unit = {
    val Chevrolet=new Car("ͨ��","ѩ����-��Ψŷ")
    val Volkswagen=new Car("һ��","����-˹�´�","2015-1-1")
    val Volvo=new Car("����","Volvo-s40","2015-1-2",666666)
    val nameArr=Array("ѩ����","����","�ֶ���")
    val carArr=Array(Chevrolet,Volkswagen,Volvo)

    outinfo(nameArr,carArr)
  }

  def outinfo(carName:Array[String],carArray:Array[Car]): Unit ={
    for (i <- 0 until carName.length){

      println(carName(i))
      println("����������Ϊ: "+carArray(i).manufactor)
      println("�����ͺ�Ϊ: "+carArray(i).model)
      println("���������Ϊ: "+carArray(i).year)
      println("�������ƺ�Ϊ: "+carArray(i).number)

    }

  }
}
