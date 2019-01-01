package vectorclock

data class Message(
  val processId: Int,
  val timestamp: Int
)