
fun main(args: Array<String>) {

  val n = 3
  val processes = Array(n) { i -> Process(i, n) }

  processes.forEach { p -> println(p) }

  println("Start")

  processes[0].start()
}

