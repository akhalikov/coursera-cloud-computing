package multicast.fifo

import java.util.*

class Channel(numOfProcesses: Int) {
  private val messages = Array(numOfProcesses) { ArrayDeque<Message>() }

  fun push(processId: Int, message: Message) {
    messages[processId].push(message)
  }

  fun pop(processId: Int): Message? {
    return if (messages[processId].isNotEmpty()) messages[processId].pop() else null
  }

  fun isEmpty(processId: Int): Boolean {
    return messages[processId].isEmpty()
  }
}