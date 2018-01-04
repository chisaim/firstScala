class Employee(var name:String,var salary:Double) {
def this(){
  this("John Q. Public",0.0)
}
}
object Employee{

  def main(args: Array[String]): Unit = {
    val ITEmployee = new Employee()
    val BankEmployee = new Employee("Tom Hask",1000)
    println("ITemployee Name: "+ITEmployee.name+" Salary: "+ITEmployee.salary)
    println("Bankemployee Name: "+BankEmployee.name+" Salary: "+BankEmployee.salary)

  }
}
