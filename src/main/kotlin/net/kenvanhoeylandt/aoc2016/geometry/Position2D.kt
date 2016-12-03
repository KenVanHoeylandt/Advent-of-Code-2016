package net.kenvanhoeylandt.aoc2016.geometry

data class Position2D(val x: Int, val y: Int) {

    fun positionWithDelta(x: Int, y: Int): Position2D {
        return Position2D(this.x + x, this.y + y)
    }
}