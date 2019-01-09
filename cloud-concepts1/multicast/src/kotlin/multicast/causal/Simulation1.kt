package multicast.causal

import multicast.Channel
import java.util.Arrays.toString

fun main(args: Array<String>) {
  val n = 4
  val mq = Channel<Message>(n)
  val p = Array(n) { i -> Process(i, n, mq) }

  println(toString(p))

  println("Start")

  p[0].multicast()  // M1

  println(toString(p))
}