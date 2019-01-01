package vectorclock

fun main(args: Array<String>) {

  val p0 = Process(0, 4)
  val p1 = Process(1, 4)
  val p2 = Process(2, 4)
  val p3 = Process(3, 4)

  p0.send(p2) // M1
  p1.instruction()
  p2.receive() // M1

  p2.send(p1) // M2
  p1.receive() // M2

  p1.send(p0) // M3
  p0.receive() // M3

  p1.send(p2) // M4

  p1.send(p0) // M5
  p0.receive() // M5
  p1.instruction()
  p0.instruction()

  p2.send(p3) // M6

  p3.instruction()
  p2.instruction()

  p1.send(p3) // M7
  p3.receive() // M7

  p3.receive() // M6

  p0.send(p2) // M8
  p2.receive() // M8

  p2.send(p3) // M9
  p3.receive() // M9

  p2.receive() // M4

  p3.send(p1) // M10
  p1.receive() // M10

  println("Simulation #1")
  println(p0)
  println(p1)
  println(p2)
  println(p3)
}