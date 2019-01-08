package consistenthash

interface HashFunction {
  fun hash(key: String): Int
}