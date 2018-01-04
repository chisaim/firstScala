class Person(var age: Int) {

  require(age < 0 ,"The age value cannot be less than zero")
  if (age < 0) age = 0

}
object Person{
  def main(args: Array[String]): Unit = {
    val age1 = 10
    val age2 = -20

    println("将Tom的年龄初始化为:"+age1)
    val Tom=new Person(age1)
    println("Tom的实际年龄为:"+Tom.age)

    println("将Tom的年龄初始化为:"+age2)
    val jack = new Person(age2)
    println("Tom的实际年龄为:"+jack.age)
  }
}
