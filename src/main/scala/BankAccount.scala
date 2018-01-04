class BankAccount {

  private var balance = 0.0

  def deposit(depamount: Double): Unit = {
    balance += depamount
  }

  def withdraw(drawamount: Double): Unit = {
    balance -= drawamount
  }

  def current = balance

}

object BankAccount {
  def main(args: Array[String]): Unit = {
    val Drawamount = 800

    val Depamount = 1000

    val acc = new BankAccount

    println("������:" + Depamount)

    acc.deposit(Depamount)

    println("���:" + acc.current)

    println("ȡ�����:" + Drawamount)

    acc.withdraw(Drawamount)

    println("���:" + acc.current)
  }
}
