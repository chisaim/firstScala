abstract class Animal {
  def run
}
class Cat extends Animal{

  override def run: Unit = println("I can run ,miao!")
}
class Dog extends Animal{
  override def run: Unit = println("I can run, wang!")
}

object AnimalTest{
  def main(args: Array[String]): Unit = {
    val cat = new Cat

    val dog = new Dog

    cat.run
    dog.run
  }
}
