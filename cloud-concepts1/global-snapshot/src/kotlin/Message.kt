class Message(
  private val type: Type,
  private val payload: String) {

  enum class Type {
    APP,
    MARKER
  }

  override fun toString(): String {
    return "Message(type=$type, '$payload')"
  }
}
