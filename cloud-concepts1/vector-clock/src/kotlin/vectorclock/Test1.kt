package vectorclock

fun main(args: Array<String>) {

  val numOfProcesses = 4

  val p0 = Process(0, numOfProcesses)
  val p1 = Process(1, numOfProcesses)
  val p2 = Process(2, numOfProcesses)
  val p3 = Process(3, numOfProcesses)

  println("Test #1")

  p0.send(p2, "M1")
  p1.instruction()
  p2.receive("M1")

  p2.send(p1, "M2")
  p1.receive("M2")

  p1.send(p0, "M3")
  p0.receive("M3")

  p1.send(p2, "M4")

  p1.send(p0, "M5")
  p0.receive("M5")

  p1.instruction()
  p0.instruction()

  p2.send(p3, "M6")

  p3.instruction()
  p2.instruction()

  p1.send(p3, "M7")
  p3.receive("M7")

  p3.receive("M6")

  p0.send(p2, "M8")
  p2.receive("M8")

  p2.send(p3, "M9")
  p3.receive("M9")

  p2.receive("M4")

  p3.send(p1, "M10")
  p1.receive("M10")

  println("Stop")

  arrayOf(p0, p1, p2, p3).forEach { println(it) }
}
