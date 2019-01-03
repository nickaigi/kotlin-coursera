open class AnPE(open val value: String) {
    init {

    }
}

class B(override val value: String) : AnPE(value)

fun main(args: Array<String>) {
    B("a")
}