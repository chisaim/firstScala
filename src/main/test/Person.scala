class Person(var age: Int) {

  require(age < 0 ,"The age value cannot be less than zero")
  if (age < 0) age = 0

}
object Person{
  def main(args: Array[String]): Unit = {
    val age1 = 10
    val age2 = -20

    println("��Tom�������ʼ��Ϊ:"+age1)
    val Tom=new Person(age1)
    println("Tom��ʵ������Ϊ:"+Tom.age)

    println("��Tom�������ʼ��Ϊ:"+age2)
    val jack = new Person(age2)
    println("Tom��ʵ������Ϊ:"+jack.age)
  }
}
