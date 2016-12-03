package net.kenvanhoeylandt.aoc2016.solutions.day1

enum class CardinalDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun getLeftSideDirection() : CardinalDirection {
        val nextIndex = if (ordinal > 0) (ordinal - 1) else values().count() - 1
        return values()[nextIndex]
    }

    fun getRightSideDirection() : CardinalDirection {
        val nextIndex = if (ordinal < (values().count() - 1)) (ordinal + 1) else 0
        return values()[nextIndex]
    }
}