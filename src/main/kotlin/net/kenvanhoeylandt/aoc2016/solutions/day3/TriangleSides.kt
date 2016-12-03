package net.kenvanhoeylandt.aoc2016.solutions.day3

data class TriangleSides(val a: Int, val b: Int, val c: Int) {
    fun isValid() : Boolean {
        return (a + b > c)
                && (a + c > b)
                && (b + c > a)
    }
}

fun triangleSidesOf(input: String): TriangleSides {
    val inputs = input.trim().split(Regex(" +"))
    if (inputs.size != 3) throw RuntimeException("invalid input \"$input\"")
    return TriangleSides(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]))
}