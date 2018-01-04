
class Rational(val n: Int, val d: Int) {
  require(d != 0, "")
  def this(n:Int)  = this(n,1)

  private val g = gcd(n.abs,d.abs)

  override def toString: String = n + "/" + d

  def add(that: Rational) = {
    new Rational(n * that.d + that.n * d, d * that.d)
  }

  def lessthan(that: Rational) = {
    this.n * this.d < this.n * this.d
  }

  def max(that: Rational) = {
    if (this.lessthan(that)) that else this
  }

  private def gcd(a: Int, b: Int):Int = {
    if(b == 0) a else gcd(b,a % b)
  }
}
