data class Person (val name: String, val age: Int)

fun main(args: Array<String>) {
    val numbers: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10)

    val even: List<Int> = numbers.filter { it % 2 == 0 }

    val squared: List<Int> = numbers.map { it * it  }

    val multipleOfThree: Int = numbers.count({ it % 3 == 0})

    val listNumbers : List<Int> = listOf(1,2,3,4)
    val listChars: List<Char> = listOf('a', 'b', 'c', 'd')

    val personList: List<Person> = arrayListOf(
        Person("Alice", 31),
        Person("Bob", 29),
        Person("Carol", 31)
    )

    //println(personList.groupBy { it.age })
    //println(personList.associateBy { it.name })
    //println(listNumbers.zip(listChars))
}