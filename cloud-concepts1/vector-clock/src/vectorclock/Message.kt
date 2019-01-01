package vectorclock

class Message(
  val processId: Int,
  val timestamps: IntArray
)