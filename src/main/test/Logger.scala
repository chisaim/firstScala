trait Logger {
def log(str:String,key:Int = 3):String
}
class CryptoLogger extends Logger{
  override def log(str: String, key: Int): String = {
    for (i <- str) yield if(key >= 0)(97 + ((i - 97 + key)%26)).toChar else (97 + ((i - 97 + 26 + key)%26)).toChar
  }
}

object CryptoLogger extends App{
  val text = "nmred"

  println(text)
  println(new CryptoLogger().log(text))
  println(new CryptoLogger().log(text, -3))
}