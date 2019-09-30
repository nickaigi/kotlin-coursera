package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

/* Svetlana's soln */
fun evaluateGuess1(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { it.first == it.second }

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { it == ch }, guess.count { it == ch })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}
/* End Svetlana's soln */

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var right: Int = 0
    for (i in 0..3){
        if (secret[i] == guess[i]){
            right++
        }
    }
    val wrong = wrongPositions(secret, guess)
    return Evaluation(right, wrong)
}

private fun wrongPositions(secret: String, guess: String): Int {
    var letters = 0
    var newSecret = ""
    var newGuess = ""

    for(i in 0..3) {
        if(secret[i] != guess[i]) {
            newSecret += secret[i]
            newGuess += guess[i]
        }
    }

    val foundLetters = mutableListOf<Char>()

    if(newSecret.isNotEmpty()) {
        for (element in guess) {
            if (!foundLetters.contains(element)) {
                val howManyInSecret = countRepetitionsOf(element, newSecret)
                val howManyInGuess = countRepetitionsOf(element, newGuess)
                if (howManyInSecret == howManyInGuess || howManyInSecret > howManyInGuess){
                    letters += howManyInGuess
                } else{
                    letters += howManyInSecret
                }

                foundLetters.add(element)
            }
        }
    }

    return letters
}

fun countRepetitionsOf(value: Char, word: String): Int {
    var count = 0
    for (element in word) {
        if (element == value) {
            count++
        }
    }
    return count
}
