package vectorclock

import java.util.Arrays
import kotlin.test.assertTrue

fun main(args: Array<String>) {

  val n = 4
  val processes = Array(n) { i -> Process(i, n) }

  val p1 = processes[0]
  val p2 = processes[1]
  val p3 = processes[2]
  val p4 = processes[3]

  println("Test #3")

  p4.send(p3, "M9")
  p1.send(p3, "M1")
  p3.receive("M9")

  p3.send(p2, "M8")
  p1.send(p2, "M2")
  p4.send(p2, "M10")

  p2.receive("M8")
  p3.receive("M1")

  p1.send(p4, "M3")
  p2.send(p3, "M6")
  p2.send(p1, "M6")

  p1.receive("M6")
  p1.send(p3, "M4")

  p4.receive("M3")
  p3.receive("M4")
  p3.receive("M6")

  p1.send(p3, "M5")
  p4.send(p3, "M11")

  p3.receive("M11")
  p3.receive("M5")
  p2.receive("M10")
  p2.receive("M2")

  println("Stop")

  assertTrue { processes.all { it.messages.isEmpty() } }

  println(Arrays.toString(processes))
}
