package multicast.causal

import multicast.Channel
import java.util.ArrayDeque
import java.util.Arrays.toString

class Process(
  private val id: Int,
  private val numOfProcesses: Int,
  private val channel: Channel<Message>
) {

  /**
   * Each receiver maintains per-sender sequence number
   */
  private val sequence = IntArray(numOfProcesses)

  private val buffer = ArrayDeque<Message>()

  companion object {
    @JvmStatic
    private var countBuffered: Int = 0
  }

  /**
   * Send multicast at process P(i):
   *
   *  - Increment local sequence number
   *  - Include incremented sequence number in multicast message
   */
  fun multicast() {
    sequence[id] += 1
    val message = Message(id, sequence)
    val sendToIds = (0 until numOfProcesses).filterNot { it == id }.toIntArray()

    log("multicast: $id -> ${toString(sendToIds)}, sequence=${toString(sequence)}")
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
    val message = channel.getLastMessage(id) ?: return

    if (isNextMessage(message) && allMulticastReceived(message)) {
      deliverMessage(message)
      checkBuffer()
    } else {
      addToBuffer(message)
    }
  }

  private fun isNextMessage(message: Message): Boolean {
    val fromId = message.processId
    return message.sequence[fromId] == sequence[fromId] + 1
  }

  private fun allMulticastReceived(message: Message): Boolean {
    return (0 until message.sequence.size)
      .filterNot { it == message.processId }
      .all { message.sequence[it] <= sequence[it] }
  }

  fun getBufferSize(): Int {
    return buffer.size
  }

  private fun addToBuffer(message: Message) {
    buffer.push(message)
    countBuffered += 1
    log("buffered=$countBuffered")
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