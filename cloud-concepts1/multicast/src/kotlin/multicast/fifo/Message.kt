package multicast.fifo

class Message(
  val processId: Int,
  val sequenceId: Int
) {

  override fun toString(): String {
    return "M(from pId=$processId, seqId=$sequenceId)"
  }
}
