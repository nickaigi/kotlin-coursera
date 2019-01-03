val String.medianChar
    get(): Char? {
        println("Calculating...")
        return getOrNull(length / 2)
    }

fun main(args: Array<String>) {
    val s = "abc"
    println(s.medianChar)
    println(s.medianChar)
}