package multicast.fifo

import java.util.ArrayDeque

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

  class Message(
    val processId: Int,
    val sequenceId: Int
  ) {

    override fun toString(): String {
      return "M(from pId=$processId, seqId=$sequenceId)"
    }
  }
}