package vectorclock

fun main(args: Array<String>) {

  val numOfProcesses = 4

  val p0 = Process(0, numOfProcesses)
  val p1 = Process(1, numOfProcesses)
  val p2 = Process(2, numOfProcesses)
  val p3 = Process(3, numOfProcesses)

  println("Test #2")

  p0.send(p2, "M1")

  p0.instruction()

  p3.send(p2, "M2")
  p2.receive("M2")

  p2.send(p3, "M4")
  p3.receive("M4")

  p2.send(p1, "M3")
  p1.receive("M3")

  p2.receive("M1")

  p2.send(p3, "M5")
  p3.receive("M5")

  p2.instruction()
  p3.instruction()

  p1.send(p2, "M6")

  p1.send(p0, "M7")
  p0.receive("M7")

  p0.send(p2, "M8")
  p2.receive("M8")

  p0.instruction()
  p0.instruction()

  p2.send(p3, "M9")
  p3.receive("M9")

  p2.receive("M6")

  p3.send(p0, "M10")
  p0.receive("M10")

  println("Stop")

  arrayOf(p0, p1, p2, p3).forEach { println(it) }
}
