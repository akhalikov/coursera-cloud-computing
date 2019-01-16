package vectorclock

import java.lang.IllegalArgumentException
import java.util.Arrays

/**
 * Simulation of a system process
 * https://www.coursera.org/learn/cloud-computing/lecture/dy8wf/2-5-vector-clocks
 */
class Process(private val id: Int, numOfProcesses: Int) {

  private val timestamps: IntArray = IntArray(numOfProcesses)
  var messages: HashMap<String, Message> = HashMap()

  fun instruction() {
    timestamps[id]++  // increment only timestamp of the process
    log("instruction")
  }

  fun send(process: Process, messageId: String) {
    timestamps[id]++  // increment only timestamp of the process

    val message = Message(timestamps.copyOf())
    process.messages[messageId] = message

    log("send $messageId -> P${process.id}")
  }

  fun receive(messageId: String) {
    val message = messages.remove(messageId)
      ?: throw IllegalArgumentException("No such message: $messageId")

    timestamps[id] += 1 // increment timestamp of the current process
    
    // for all other processes: Vi[j] = max(V_message[j], Vi[j]), for j != i
    for (i in timestamps.indices) {
      if (i != id) {
        timestamps[i] = Math.max(message.timestamps[i], timestamps[i])
      }
    }

    log("received $message")
  }

  private fun log(message: String) {
    println("$this: $message")
  }

  override fun toString(): String {
    return "P$id${Arrays.toString(timestamps)}"
  }

  /**
   * Inter-process message
   */
  class Message(val timestamps: IntArray) {
    override fun toString(): String {
      return "M${Arrays.toString(timestamps)}"
    }
  }
}
