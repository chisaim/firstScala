import java.awt.geom.Ellipse2D

trait RectangleLike {
this : Ellipse2D.Double =>
  def translate(x:Double,y:Double): Unit ={
    this.x = x
    this.y = y
  }

  def grow(x:Double,y:Double): Unit ={
    this.x += x
    this.y += y

  }


}
object EclipseTest extends App{
  val egg = new Ellipse2D.Double(5,10,20,30) with RectangleLike
  egg.translate(10,-10)
  egg.grow(10,20)
  println(egg.getX)
  println(egg.getY)
}
