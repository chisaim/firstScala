object Testswap extends App {

def swap[S,T](tup : (S,T)):(T,S) ={
  tup match {
    case (a,b) => (b,a)
  }
}

  println(swap(2,3))
}
