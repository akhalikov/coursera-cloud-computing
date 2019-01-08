package consistenthash

import java.util.*

class ConsistentHash(
  private val numOfReplicas: Int,
  private val hashFunction: HashFunction,
  nodes: Array<Node>) {

  private val hashRing: TreeMap<Int, Node> = TreeMap()

  init {
    for (node in nodes) {
      addNodeToHashRing(node)
    }
  }

  private fun addNodeToHashRing(node: Node) {
    for (i in 0..numOfReplicas) {
      val hash = hashFunction.hash(node.address + i)
      hashRing.put(hash, node)
    }
  }

  
}