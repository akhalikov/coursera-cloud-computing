package multicast.fifo

import multicast.Channel
import java.util.ArrayDeque
import java.util.Arrays.toString

class Process(
  val id: Int,
  private val numOfProcesses: Int,
  private val channel: Channel<Message>
) {

  /**
   * Each receiver maintains per-sender sequence number
   */
  val sequence = IntArray(numOfProcesses)

  private val buffer:ArrayDeque<Message> = ArrayDeque()

  companion object {
    @JvmStatic
    var countBuffered: Int = 0
  }

  /**
   * Send multicast at process P(i):
   *
   *  - Increment local sequence number
   *  - Include incremented sequence number in multicast message
   */
  fun multicast(messageKey: String) {
    sequence[id] += 1
    val message = Message(id, sequence[id], messageKey)
    val sendToIds = (0 until numOfProcesses).filterNot { it == id }.toIntArray()

    println()
    log("multicast($messageKey) -> ${toString(sendToIds)}, vector=${toString(sequence)}")
    sendToIds.forEach { id -> channel.putMessage(id, message) }
  }

  /**
   * Receive multicast at process P_i from P_j:
   *
   *  - Let S be seq. number from message
   *  - If (S == P_i [ j ]):
   *    |
   *    |- deliver message to an application
   *    |_ increment seq. number at index j
   *
   *  - else:
   *    |_ buffer this multicast until above condition is true
   */
  fun receive() {
    val message = channel.getLatestMessage(id) ?: return
    processReceive(message)
  }

  fun receiveOldest() {
    val message = channel.getOldestMessage(id) ?: return
    processReceive(message)
  }

  private fun processReceive(message: Message) {
    val fromId = message.processId
    if (message.sequenceId == sequence[fromId] + 1) {
      deliverMessage(message)
      checkBuffer()
    } else {
      addToBuffer(message)
    }
  }

  fun getBufferSize(): Int {
    return buffer.size
  }

  private fun addToBuffer(message: Message) {
    buffer.offer(message)
    countBuffered += 1
    log("buffered ${message.key}, count=$countBuffered")
  }

  private fun checkBuffer() {
    if (buffer.isNotEmpty()) {
      deliverMessage(buffer.poll())
    }
  }

  private fun deliverMessage(message: Message) {
    val fromId = message.processId
    sequence[fromId] += 1
    log("delivered $message, vector=${toString(sequence)}")
  }

  private fun log(str: String) {
    println("Process($id): $str")
  }

  override fun toString(): String {
    return "Process(id=$id, ${toString(sequence)})"
  }
}
