package net.kenvanhoeylandt.aoc2016.solutions.day2

import net.kenvanhoeylandt.aoc2016.geometry.Position2D

/*
    1 2 3
    4 5 6
    7 8 9

    translates to positions:

    (-1,-1) (0, -1) (1, -1)
    (-1, 0) (0,  0) (1,  0)
    (-1, 1) (0,  1) (1,  1)
 */
fun getNumberFromDirectionsPartOne(directions: List<Direction>): Char {

    var currentPosition = Position2D(0, 0)

    for (direction in directions) {
        when (direction) {
            Direction.UP -> {
                if (currentPosition.y > -1) {
                    currentPosition = currentPosition.positionWithDelta(0, -1)
                }
            }
            Direction.DOWN -> {
                if (currentPosition.y < 1) {
                    currentPosition = currentPosition.positionWithDelta(0, 1)
                }
            }
            Direction.LEFT -> {
                if (currentPosition.x > -1) {
                    currentPosition = currentPosition.positionWithDelta(-1, 0)
                }
            }
            Direction.RIGHT -> {
                if (currentPosition.x < 1) {
                    currentPosition = currentPosition.positionWithDelta(1, 0)
                }
            }
            else -> throw RuntimeException("unknown direction: $direction")
        }
    }

    return getDigitPartOne(currentPosition)
}

fun getDigitPartOne(position: Position2D): Char {
    if (position.x == -1 && position.y == -1) return '1'
    else if (position.x == 0 && position.y == -1) return '2'
    else if (position.x == 1 && position.y == -1) return '3'
    else if (position.x == -1 && position.y == 0) return '4'
    else if (position.x == 0 && position.y == 0) return '5'
    else if (position.x == 1 && position.y == 0) return '6'
    else if (position.x == -1 && position.y == 1) return '7'
    else if (position.x == 0 && position.y == 1) return '8'
    else if (position.x == 1 && position.y == 1) return '9'
    else throw RuntimeException("unknown position $position")
}