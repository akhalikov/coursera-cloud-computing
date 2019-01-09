package multicast

import java.util.ArrayDeque
import java.util.Arrays

/**
 * Emulation of inter-process communication channel
 */
class Channel<T>(numOfProcesses: Int) {
  private val messages = Array(numOfProcesses) { ArrayDeque<T>() }

  fun putMessage(processId: Int, message: T) {
    messages[processId].push(message)
  }

  fun getLastMessage(processId: Int): T? {
    return if (messages[processId].isNotEmpty()) messages[processId].pop() else null
  }

  fun isEmpty(processId: Int): Boolean {
    return messages[processId].isEmpty()
  }
}