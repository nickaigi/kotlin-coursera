package nicestring

fun noIllegalStrings(word: String): Boolean {
    if (word.contains("bu") || word.contains("ba") || word.contains("be")){
        return false
    }
    return true
}

fun containsThreeVowels(word: String): Boolean {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    var vowelCount = 0
    for (w in word){
        if (w in vowels){
            if (++vowelCount == 3){
                return true
            }
        }
    }
    return false
}

fun containsDoubleLetter(word: String): Boolean {
    var isRepeated: Boolean = false
    var former: Char = '1' // temporary place holder

    for (w in word){
        if (w == former){
            isRepeated = true
        } else {
            former = w
        }
    }
    return isRepeated
}

fun String.isNice(): Boolean {
    /* Svetlana's Soln
     * --------------------------------------------------------------------
     *
     * val noBadString = setOf("ba", "be", "bu").none( this.contains(it) }
     * val hasThreeVowels = count { it in "aeiou" } >= 3
     * val hasDouble = zipWithNext().any { it.first == it.second }
     *
     * *** Alternative return statements:
     *
     * return listOf(noBadString, hasThreeVowels, hasDouble).filter { it == true }.size > 2
     *
     * return listOf(noBadString, hasThreeVowels, hasDouble).filter { it }.size > 2
     *
     * return listOf(noBadString, hasThreeVowels, hasDouble).count { it } >= 2
     */
    var passesTwoTests: Boolean = false
    val passesNoIllegalStrings: Boolean = noIllegalStrings(this)
    println("$this - passesNoIllegalStrings: $passesNoIllegalStrings")

    val passesContainsDoubleLetter: Boolean = containsDoubleLetter(this)
    println("$this - passesContainsDoubleLetter: $passesContainsDoubleLetter")

    val passesContainsThreeVowels: Boolean = containsThreeVowels(this)
    println("$this - passesContainsThreeVowels: $passesContainsThreeVowels")


    if (!passesNoIllegalStrings && passesContainsDoubleLetter && passesContainsThreeVowels){
        passesTwoTests = true
    }
    if (passesNoIllegalStrings && !passesContainsDoubleLetter && passesContainsThreeVowels){
        passesTwoTests = true
    }
    if (passesNoIllegalStrings && passesContainsDoubleLetter && !passesContainsThreeVowels){
        passesTwoTests = true
    }
    if (passesNoIllegalStrings && passesContainsDoubleLetter && passesContainsThreeVowels){
        // all tests passed
        passesTwoTests = true
    }
    return passesTwoTests
}