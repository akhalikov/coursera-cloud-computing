class Message(private val type: Type) {

  enum class Type {
    APP,
    MARKER
  }

  override fun toString(): String {
    return "Message(type=$type)"
  }
}
