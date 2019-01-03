enum class Color(
    val r: Int, val g: Int, val b:Int
) {
    BLUE(0,0,255), ORANGE(255, 165, 0), RED(255,0,0);
    fun rgb() = (r * 256 + g) * 256 + b
}


fun main(args: Array<String>) {
    println(Color.BLUE.r)
    println(Color.BLUE.rgb())
}