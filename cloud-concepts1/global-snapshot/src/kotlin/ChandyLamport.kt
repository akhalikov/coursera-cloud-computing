
class Process(private val id: Int) {

  fun recordState() {
  }

  fun sendMarker(channel: Channel) {
  }

  fun listen() {
  }

  override fun toString(): String {
    return "P($id)"
  }
}

class Channel(private val idFrom: Int,
              private val idTo: Int)

fun main(args: Array<String>) {

  val n = 3
  val processes = Array(n) { i -> Process(i)}

  println(processes.size)
  println(processes[0])
}

