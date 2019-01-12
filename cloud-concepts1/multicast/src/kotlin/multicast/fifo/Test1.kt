package multicast.fifo

import multicast.Channel
import java.util.Arrays.toString
import kotlin.test.assertTrue

fun main(args: Array<String>) {
  val n = 4
  val mq = Channel<Message>(n)
  val p = Array(n) { i -> Process(i, n, mq) }

  println(toString(p))

  println("Start")

  p[1].multicast("M1")
  p[0].receive()    // M1

  p[2].receive()    // M1
  p[2].multicast("M2")

  p[1].receive()    // M2

  p[3].multicast("M3")
  p[3].receive()    // M2

  p[0].receive()    // M3 before M2
  p[0].receive()    // M2
  p[1].receive()    // M3

  p[2].multicast("M4")
  p[2].receive()    // M3

  p[0].receive()    // M4
  p[3].receive()    // M4

  p[0].multicast("M5")
  p[3].receive()    // M5
  p[2].receive()    // M5

  p[1].multicast("M6")

  p[0].receive()    // M6

  p[1].receive()    // M5
  p[1].receive()    // M4

  p[2].receive()    // M6

  p[3].receive()    // M6
  p[3].receive()    // M1

  println()
  println("Stop")

  println(toString(p))
  println("Total buffered: ${Process.countBuffered}")

  assertTrue { p.all { process -> mq.isEmpty(process.id) } }
  assertTrue(p.all { it.getBufferSize() == 0 })
}
