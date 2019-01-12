package multicast

import java.util.ArrayDeque

/**
 * Emulation of inter-process communication channel
 */
class Channel<T>(numOfProcesses: Int) {
  private val messages = Array(numOfProcesses) { ArrayDeque<T>() }

  fun putMessage(processId: Int, message: T) {
    messages[processId].push(message)
  }

  fun getLatestMessage(processId: Int): T? {
    return if (messages[processId].isNotEmpty()) messages[processId].pop() else null
  }

  fun getOldestMessage(processId: Int): T? {
    return if (messages[processId].isNotEmpty()) messages[processId].pollLast() else null
  }

  fun isEmpty(processId: Int): Boolean {
    return messages[processId].isEmpty()
  }
}
