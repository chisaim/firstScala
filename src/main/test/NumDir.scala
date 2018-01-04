import java.io.File

object NumDir extends App {

  val path = "."

  val dir = new File(path)

  def subdirs(dirs:File):Iterator[File] = {
    val children = dirs.listFiles().filter(_.getName.endsWith("class"))

    children.toIterator ++ dir.listFiles().filter(_.isDirectory).toIterator.flatMap(subdirs _)

  }

  val n  = subdirs(dir).length
  println(n)
}
