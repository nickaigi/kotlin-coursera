fun main(args: Array<String>) {
    val s = null
    println(s as? Int)    // null
    println(s as Int?)    // exception
}