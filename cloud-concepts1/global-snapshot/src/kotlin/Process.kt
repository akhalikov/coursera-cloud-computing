class Process(
  private val id: Int,
  private val numOfProcesses: Int) {

  private var state: Int = 0
  private var markerCounter: Int = 0
  val input: HashMap<Int, Channel> = HashMap()
  private val output: HashMap<Int, Channel> = HashMap()

  init {
    for (index in 0 until numOfProcesses) {
      if (index != id) {
        input[index] = Channel(index, id)
        output[index] = Channel(id, index)
      }
    }
  }

  fun start() {
    recordState()
    sendMarkers()
    listen()
  }

  fun sendAppMessage(processId: Int) {
    sendMessage(processId, Message.Type.APP)
  }

  private fun sendMarker(processId: Int) {
    sendMessage(processId, Message.Type.MARKER)
  }

  private fun recordState() {
    state += 1
    log("recorded state=$state")
  }

  private fun sendMarkers() {
    (0 until numOfProcesses)
      .filterNot { processId -> processId == id }
      .map { processId -> sendMarker(processId) }
  }

  private fun sendMessage(processId: Int, messageType: Message.Type) {
    log("sending $messageType message to Process(id=$processId)")
    output[processId]?.putMessage(Message(messageType))
  }

  private fun listen() {
    log("start listening input channels")
  }

  private fun log(str: String) {
    println("P($id): $str")
  }

  override fun toString(): String {
    return "Process(id=$id, state=S$state, "
      .plus("input=" + input.values.joinToString())
      .plus(", ")
      .plus("output=" + output.values.joinToString())
      .plus(")")
  }
}