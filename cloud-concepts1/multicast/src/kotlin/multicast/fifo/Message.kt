package multicast.fifo

class Message(
  val processId: Int,
  val sequenceId: Int,
  val key: String
) {

  override fun toString(): String {
    return "Message($key, from pId=$processId, sequenceId=$sequenceId)"
  }
}
