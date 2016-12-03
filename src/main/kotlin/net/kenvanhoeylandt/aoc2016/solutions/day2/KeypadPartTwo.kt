package net.kenvanhoeylandt.aoc2016.solutions.day2

import net.kenvanhoeylandt.aoc2016.geometry.Position2D

/*
    Keypad:

        1
      2 3 4
    5 6 7 8 9
      A B C
        D

    Where '7' is coordinate (0, 0)
 */
fun getNumberFromDirectionsPartTwo(directions: List<Direction>): Char {

    var currentPosition = Position2D(0, 0)

    for (direction in directions) {
        when (direction) {
            Direction.UP -> {
                val canGoUp = (currentPosition.x > -2 && currentPosition.x < 2 && currentPosition.y > -1)
                    || (currentPosition.x == 0 && currentPosition.y > -2)

                if (canGoUp) {
                    currentPosition = currentPosition.positionWithDelta(0, -1)
                }
            }
            Direction.DOWN -> {
                val canGoDown = (currentPosition.x > -2 && currentPosition.x < 2 && currentPosition.y < 1)
                        || (currentPosition.x == 0 && currentPosition.y < 2)

                if (canGoDown) {
                    currentPosition = currentPosition.positionWithDelta(0, 1)

                }
            }
            Direction.LEFT -> {
                val canGoLeft = (currentPosition.y > -2 && currentPosition.y < 2 && currentPosition.x > -1)
                        || (currentPosition.x > -2 && currentPosition.y == 0)

                if (canGoLeft) {
                    currentPosition = currentPosition.positionWithDelta(-1, 0)
                }
            }
            Direction.RIGHT -> {
                val canGoRight = (currentPosition.y > -2 && currentPosition.y < 2 && currentPosition.x < 1)
                        || (currentPosition.x < 2 && currentPosition.y == 0)

                if (canGoRight) {
                    currentPosition = currentPosition.positionWithDelta(1, 0)
                }
            }
            else -> throw RuntimeException("unknown direction: $direction")
        }

        val test = getDigitPartTwo(currentPosition)
    }

    return getDigitPartTwo(currentPosition)
}

fun getDigitPartTwo(position: Position2D): Char {
    if (position.x == -1 && position.y == -1) return '2'
    else if (position.x == 0 && position.y == -1) return '3'
    else if (position.x == 1 && position.y == -1) return '4'
    else if (position.x == -1 && position.y == 0) return '6'
    else if (position.x == 0 && position.y == 0) return '7'
    else if (position.x == 1 && position.y == 0) return '8'
    else if (position.x == -1 && position.y == 1) return 'A'
    else if (position.x == 0 && position.y == 1) return 'B'
    else if (position.x == 1 && position.y == 1) return 'C'
    else if (position.x == 0 && position.y == -2) return '1'
    else if (position.x == 0 && position.y == 2) return 'D'
    else if (position.x == -2 && position.y == 0) return '5'
    else if (position.x == 2 && position.y == 0) return '9'
    else throw RuntimeException("unknown position $position")
}