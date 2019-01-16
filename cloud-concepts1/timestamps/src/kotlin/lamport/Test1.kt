package lamport

import java.util.Arrays
import kotlin.test.assertTrue

fun main(args: Array<String>) {
  val p1 = Process(1)
  val p2 = Process(2)
  val p3 = Process(3)
  val p4 = Process(4)

  println("Test #1: Lamport")

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

  val processes = arrayOf(p1, p2, p3, p4)

  assertTrue { processes.all { it.messages.isEmpty() } }

  println(Arrays.toString(processes))
}