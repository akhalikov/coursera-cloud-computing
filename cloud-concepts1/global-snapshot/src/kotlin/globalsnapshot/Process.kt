package globalsnapshot

import java.util.*
import kotlin.collections.HashMap

class Process(
  private val id: Int) {

  private var state = 0
  private val messages = HashMap<Int, Queue<Message>>()

  private fun log(str: String) {
    println("P($id): $str")
  }

  override fun toString(): String {
    return "multicast.fifo.globalsnapshot.Process(id=$id, S$state)"
  }
}