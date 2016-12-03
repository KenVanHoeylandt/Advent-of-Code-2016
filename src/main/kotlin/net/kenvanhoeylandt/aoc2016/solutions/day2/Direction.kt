package net.kenvanhoeylandt.aoc2016.solutions.day2

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun directionOf(character: Char): Direction {
    when (character) {
        'U' -> return Direction.UP
        'D' -> return Direction.DOWN
        'L' -> return Direction.LEFT
        'R' -> return Direction.RIGHT
        else -> throw IllegalArgumentException("unsupported character \"$character\"")
    }
}

fun directionOf(characters: CharArray) : List<Direction> {
    return (0..characters.lastIndex).map { directionOf(characters[it]) }
}