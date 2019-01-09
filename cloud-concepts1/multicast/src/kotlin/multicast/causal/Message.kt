package multicast.causal

import java.util.Arrays.toString

class Message(
  val processId: Int,
  val sequence: IntArray
) {

  override fun toString(): String {
    return "M(from pId=$processId, ${toString(sequence)})"
  }
}