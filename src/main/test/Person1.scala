class Person1(val name: String) {

  private val namearr = name.split("-")

  def firstname = namearr(0)
  def lastname = namearr(1)
}
object Person1{
  def main(args: Array[String]): Unit = {
    val person = new Person1("XinYu-Jiang")

    println("person������Ϊ:"+person.name)
    println("person��FisrtName:"+person.firstname)
    println("person��LastName:"+person.lastname)

  }
}
