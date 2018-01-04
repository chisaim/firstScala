
import scala.beans.{BeanDescription, BeanProperty}

class Student {
  @BeanProperty
  @BeanDescription("llllllll")
  var name: String = null

  @BeanProperty
  var id: Long = 0
}
