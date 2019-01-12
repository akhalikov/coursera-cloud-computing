package multicast.causal

import java.util.Arrays.toString

class Message(
  val processId: Int,
  val sequence: IntArray,
  val key: String
) {

  override fun toString(): String {
    return "Message($key, from pId=$processId, ${toString(sequence)})"
  }
}
