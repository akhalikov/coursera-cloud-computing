package multicast.fifo

import java.util.*
import java.util.Arrays.toString

class Process(
  private val id: Int,
  private val numOfProcesses: Int,
  private val channel: Channel) {

  private val sequence = IntArray(numOfProcesses)
  private val buffer = ArrayDeque<Message>()

  companion object {
    @JvmStatic
    private var countBuffered: Int = 0
  }

  fun multicast() {
    sequence[id] += 1
    val message = Message(id, sequence[id])
    val ids = (0 until numOfProcesses)
      .filterNot { it == id }
      .toIntArray()
    log("multicast: $id -> ${toString(ids)}, sequence=${Arrays.toString(sequence)}")
    ids.forEach { id -> channel.push(id, message) }
  }

  fun receive() {
    val message = channel.pop(id)
    if (message != null) {
      val fromId = message.processId
      if (message.sequenceId == sequence[fromId] + 1) {
        deliverMessage(message)
        checkBuffer()
      } else {
        buffer.push(message)
        countBuffered++
        log("buffered=$countBuffered")
      }
    }
  }

  fun isEmptyBuffer(): Boolean {
    return buffer.isEmpty()
  }

  private fun checkBuffer() {
    if (buffer.isNotEmpty()) {
      deliverMessage(buffer.pop())
    }
  }

  private fun deliverMessage(message: Message) {
    log("message delivered: $message")
    sequence[message.processId] += 1
  }

  private fun log(str: String) {
    println("Process($id): $str")
  }

  override fun toString(): String {
    return "Process(id=$id, [${toString(sequence)}])"
  }
}