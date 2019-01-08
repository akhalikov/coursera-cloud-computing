package globalsnapshot

class Message(
  private val type: Type,
  private val payload: String) {

  enum class Type {
    APP,
    MARKER
  }

  override fun toString(): String {
    return "globalsnapshot.Message(type=$type, '$payload')"
  }
}
