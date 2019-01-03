package vectorclock

import java.util.LinkedList

/**
 * Simulation of a system process
 * https://www.coursera.org/learn/cloud-computing/lecture/dy8wf/2-5-vector-clocks
 */
class Process(private val id: Int,
              numOfProcesses: Int,
              private val logTimestamps: Boolean = false) {

  private val timestamps: IntArray = IntArray(numOfProcesses)

  var buffer: LinkedList<Message> = LinkedList()

  fun instruction() {
    timestamps[id]++  // increment only timestamp of the process
    log()
  }

  fun send(process: Process) {
    timestamps[id]++  // increment only timestamp of the process
    process.putMessage(timestamps)
    log()
  }

  fun receive() {
    val message = buffer.poll()    
    timestamps[id] += 1 // increment timestamp of the current process
    
    // for all other processes: Vi[j] = max(V_message[j], Vi[j]), for j != i
    for (i in timestamps.indices) {
      if (i != id) {
        timestamps[i] = Math.max(message.timestamps[i], timestamps[i])
      }
    }
    log()
  }

  private fun putMessage(timestamps: IntArray) {
    buffer.push(Message(timestamps))
  }

  private fun log() {
    if (logTimestamps) {
      println(this)
    }
  }

  override fun toString(): String {
    val description = timestamps.joinToString(separator = ",")
    return "P$id($description)"
  }

  /**
   * Inter-process message
   */
  class Message(
    val timestamps: IntArray
  )
}
