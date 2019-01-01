package vectorclock

import java.util.*

class Process(private val id: Int,
              numOfProcesses: Int) {

  var localTime: Int = 0
  var timestamps: IntArray = IntArray(numOfProcesses)

  var buffer: LinkedList<Message> = LinkedList()

  fun instruction(): Int {
    localTime++
    if (localTime == 6) {
      print("concurrent=" + this)
    }

    timestamps[id] = localTime
    return localTime
  }

  fun send(process: Process) {
    instruction()
    process.putMessage(id, localTime)
  }

  fun putMessage(processId: Int, timestamp: Int) {
    buffer.push(Message(processId, timestamp))
  }

  fun receive() {
    val message = buffer.poll()

    localTime = Math.max(localTime, message.timestamp) + 1
    timestamps[message.processId] = message.timestamp
    timestamps[id] = localTime
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Process

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id
  }

  override fun toString(): String {
    val descr = timestamps.joinToString(separator = ",")
    return "P$id($descr)"
  }
}