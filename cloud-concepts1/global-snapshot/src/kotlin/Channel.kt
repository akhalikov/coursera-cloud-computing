import java.util.*

class Channel(
  private val source: Process,
  private val dest: Process) {
  private var messages: Queue<Message> = ArrayDeque()

  fun putMessage(message: Message) {
    to.
  }

  override fun toString(): String {
    return "[$idFrom -> $idTo]"
  }
}