package lamport

/**
 * Simulation of a system process
 * https://www.coursera.org/learn/cloud-computing/lecture/4nPtE/2-4-lamport-timestamps
 */
class Process(private val id: Int) {

  private var timestamp: Int = 0
  var messages: HashMap<String, Message> = HashMap()

  fun send(process: Process, messageId: String) {
    timestamp += 1

    val message = Message(messageId, timestamp)
    process.messages[messageId] = message

    log("send $message -> P${process.id}")
  }

  fun receive(messageId: String) {
    val message = messages.remove(messageId)
      ?: throw IllegalArgumentException("No such message: $messageId")

    timestamp = Math.max(timestamp, message.timestamp) + 1

    log("received $message")
  }

  private fun log(message: String) {
    println("$this: $message")
  }

  override fun toString(): String {
    return "P$id(ts=$timestamp)"
  }

  /**
   * Inter-process message
   */
  class Message(
    private val id: String,
    val timestamp: Int
  ) {
    override fun toString(): String {
      return "Message $id($timestamp)"
    }
  }
}
