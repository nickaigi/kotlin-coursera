import java.lang.IllegalArgumentException

//class Student constructor(name: String, age: Int){
//    val name: String
//    var age: Int

    // Secondary constructor
    //constructor(name: String, age: Int){
    //    this.name = name
    //    this.age = age
    //}
//    init {
//        this.name = name
//        this.age = age
//    }
//}


//class Student constructor(val name: String, var age:Int)

//class Student constructor(val name: String, var age:Int = 0)

class Student constructor(val name: String){
    var age:Int = 10
    set(value){
        if (value < 10) throw IllegalArgumentException("Must be older than 10")
        field = value
    }
}

fun main(args: Array<String>) {
    val s1: Student = Student("nick")
    s1.age = 9
    println(s1.name +" "+s1.age)

}