fun duplicateNonZero(list: List<Int>): List<Int> {
    return list.flatMap {
        if (it == 0) return listOf()
        listOf(it, it)
    }
}

fun main(args: Array<String>) {
    println(duplicateNonZero(listOf(3, 0, 5)))
}