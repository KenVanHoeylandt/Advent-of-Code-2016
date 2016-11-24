package net.kenvanhoeylandt.aoc2016

data class ApplicationParameters(val day: Int, val sessionToken: String)

fun applicationParameters(args: Array<String>): ApplicationParameters {
    if (args.size != 2) {
        throw IllegalArgumentException("Expected 2 arguments (first is session token, second is day number")
    }

    val sessionToken = args[0]
    val day_string = args[1]
    val day = Integer.valueOf(day_string)!!

    return ApplicationParameters(day, sessionToken)
}
