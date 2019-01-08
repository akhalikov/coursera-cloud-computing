package globalsnapshot

import java.util.*

fun main(args: Array<String>) {

  val n = 2
  val processes = Array(n) { i -> Process(i) }

  println(Arrays.toString(processes))

  println("Start")
}

