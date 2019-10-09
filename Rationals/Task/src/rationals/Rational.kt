package rationals

import java.math.BigInteger

open class Rational(numerator: BigInteger, denominator: BigInteger = BigInteger.ONE): Comparable<Rational>{

    val n = numerator
    val d = if (denominator != BigInteger.ZERO ) denominator else throw IllegalArgumentException()

    override fun toString(): String {
        return when {
            d == 1.toBigInteger() || n.rem(d) == 0.toBigInteger() -> n.div(d).toString()
            else -> {
                val r = simplifyFraction(this)

                if(r.d < 0.toBigInteger() || (r.n < 0.toBigInteger() && r.d < 0.toBigInteger())){
                    fixRational(Rational(r.n.negate(), r.d.negate()))
                } else {
                    fixRational(Rational(r.n, r.d))
                }
            }
        }
    }

    override fun compareTo(other: Rational): Int {
        val ratio = n.toFloat() / d.toFloat()
        val numberRatio = other.n.toFloat() / other.d.toFloat()
        if (ratio > numberRatio) {
            return 1
        } else if (ratio == numberRatio) {
            return 0
        }
        return -1
    }

    /* must implement equals - this fucking section took me 1.5 hrs to know that I needed an equals()*/
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as Rational

        val thisN = simplifyFraction(this)
        val otherN = simplifyFraction(other)

        return thisN.n.toDouble().div(thisN.d.toDouble()) == (otherN.n.toDouble().div(otherN.d.toDouble()))
    }
}

fun Number.toBigInteger() = BigInteger(this.toString())

infix fun Int.divBy(denominator: Int): Rational {
    require(denominator != 0)

    return Rational(this.toBigInteger(), denominator.toBigInteger())
}

infix fun Long.divBy(denominator: Long): Rational {
    require(denominator != 0L)
    return Rational(this.toBigInteger(), denominator.toBigInteger())
}

infix fun BigInteger.divBy(denominator: BigInteger): Rational {
    require(denominator != 0.toBigInteger())
    return Rational(this.toBigInteger(), denominator.toBigInteger())
}

operator fun Rational.div(other: Rational): Rational {
    val n = n * other.d
    val d = d * other.n
    return Rational(n, d)
}

operator fun Rational.times(other: Rational): Rational {
    val n = n * other.n
    val d = d * other.d
    return Rational(n, d)
}

/*operator fun Rational.minus(other: Rational): Rational {
    val d1 = d
    val d2 = other.d
    val gcd = d2.toBigInteger().gcd(d1.toBigInteger())

    val n = (gcd / n.toBigInteger()) - (gcd / other.n.toBigInteger())
    return Rational(n, gcd)
}
*/
operator fun Rational.minus(other: Rational) : Rational{
    val gcm = d * other.d
    val numerator = (gcm / d) * n - (gcm / other.d) * other.n
    return Rational(numerator, gcm)
}
/*operator fun Rational.plus(other: Rational): Rational {
    val d1 = d
    val d2 = other.d
    val gcd = d2.toBigInteger().gcd(d1.toBigInteger())

    val n = (gcd / n.toBigInteger()) + (gcd / other.n.toBigInteger())
    return Rational(n, gcd)
}
 */

operator fun Rational.plus(other: Rational): Rational = (n.times(other.d).plus(other.n.times(d))).divBy(other.d.times(d))

operator fun Rational.unaryMinus(): Rational{
    return Rational(n.negate(), d)
}

operator fun Rational.rangeTo(end: Rational): ClosedRange<Rational> {
    return object : ClosedRange<Rational> {
        override val endInclusive: Rational = end
        override val start: Rational = this@rangeTo
    }
}

operator fun Pair<Rational, Rational>.contains(other: Rational): Boolean {
    if (other > this.first && other < this.second) return true
    return false
}

fun String.toRational(): Rational {
    val number = split("/")

    return when {
        number.size == 1 -> Rational(number[0].toBigInteger(), 1.toBigInteger())
        else -> Rational(number[0].toBigInteger(), number[1].toBigInteger())
    }
}

fun fixRational(other: Rational) : String = other.n.toString() + "/" + other.d.toString()


fun hcf(a: BigInteger, b: BigInteger): BigInteger =
    if (b != 0.toBigInteger()) hcf(b, a % b) else a

fun simplifyFraction(other: Rational): Rational {
    val hcf = hcf(other.n, other.d).abs()
    return Rational(other.n.div(hcf), other.d.div(hcf))
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}