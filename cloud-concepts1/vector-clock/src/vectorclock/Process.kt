package vectorclock

import java.util.*

class Process(private val id: Int,
              numOfProcesses: Int,
              private val printStatus: Boolean = false) {

  var timestamps: IntArray = IntArray(numOfProcesses)

  var buffer: LinkedList<Message> = LinkedList()

  fun instruction() {
    timestamps[id] += 1
    if (printStatus) {
      println(this)
    }
  }

  fun send(process: Process) {
    timestamps[id] += 1
    process.putMessage(id, timestamps)
    if (printStatus) {
      println(this)
    }
  }

  fun putMessage(processId: Int, timestamps: IntArray) {
    buffer.push(Message(processId, timestamps))
  }

  fun receive() {
    val message = buffer.poll()

    timestamps[id] += 1
    for (i in timestamps.indices) {
      if (i != id) {
        timestamps[i] = Math.max(message.timestamps[i], timestamps[i])
      }
    }
    if (printStatus) {
      println(this)
    }
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
    val description = timestamps.joinToString(separator = ",")
    return "P$id($description)"
  }
}