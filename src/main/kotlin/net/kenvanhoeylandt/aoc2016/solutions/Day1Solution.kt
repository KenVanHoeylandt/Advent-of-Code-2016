package net.kenvanhoeylandt.aoc2016.solutions

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution

class Day1Solution : Solution(1) {

    @Throws(Exception::class)
    override fun solvePartOne(input: String): String {
        // read input
        val inputOperations = readInput(input)
        // convert to absolute movements
        val absoluteOperations = getAbsoluteOperations(inputOperations);

        var currentX = 0
        var currentY = 0

        // update currentX and currentY until we arrived at our destination
        absoluteOperations.forEach {
            when (it.cardinalDirection) {
                CardinalDirection.NORTH -> currentY -= it.blocks
                CardinalDirection.EAST -> currentX += it.blocks
                CardinalDirection.SOUTH -> currentY += it.blocks
                CardinalDirection.WEST -> currentX -= it.blocks
            }
        }

        val distanceTravelled = getDistanceTravelled(currentX, currentY)

        return "travelled $distanceTravelled blocks to ($currentX, $currentY)"
    }

    @Throws(Exception::class)
    override fun solvePartTwo(input: String): String {
        // read input
        val inputOperations = readInput(input)
        // convert to absolute movements
        val absoluteOperations = getAbsoluteOperations(inputOperations);
        // this set stores unique x-y coordinates
        val coordinatesSet = mutableSetOf<String>()
        // add the starting coordinate to set
        coordinatesSet.add("0,0")

        // variables holding the current state as we travel
        var currentX = 0
        var currentY = 0
        var distanceTravelled = -1

        // update currentX and currentY until we arrived at the first revisited block
        mainloop@ for ((cardinalDirection, blocks) in absoluteOperations) {

            for (i in 1..blocks) {
                when (cardinalDirection) {
                    CardinalDirection.NORTH -> currentY -= 1
                    CardinalDirection.EAST -> currentX += 1
                    CardinalDirection.SOUTH -> currentY += 1
                    CardinalDirection.WEST -> currentX -= 1
                }

                val currentCoordinate = "$currentX,$currentY"

                // check if the coordinate exists
                if (coordinatesSet.contains(currentCoordinate)) {
                    distanceTravelled = getDistanceTravelled(currentX, currentY)
                    break@mainloop
                } else {
                    coordinatesSet.add(currentCoordinate)
                }
            }
        }

        // return the solution if we have it
        if (distanceTravelled != -1) {
            return "travelled $distanceTravelled blocks to ($currentX, $currentY)"
        } else {
            return "solution not found"
        }
    }

    /**
     * Read the raw input and return it as a list of operations.
     * @return a list of InputOperation instances with relative input commands
     */
    private fun readInput(input: String): List<InputOperation> {
        return Observable.just(input)
                .flatMapIterable { it.split(", ") }
                .map { InputOperation(it.substring(0, 1), it.substring(1).toInt()) }
                .toList()
                .blockingGet()
    }

    /**
     * @param inputOperations relative movement operation list
     * @return absolute movement operation list
     */
    private fun getAbsoluteOperations(inputOperations: List<InputOperation>): List<AbsoluteOperation> {
        var currentDirection = CardinalDirection.NORTH

        return inputOperations.map {
            if ("L" == it.direction) {
                currentDirection = currentDirection.getLeftSideDirection()
            } else if ("R" == it.direction) {
                currentDirection = currentDirection.getRightSideDirection()
            } else {
                throw RuntimeException("invalid input: " + it.direction)
            }

            AbsoluteOperation(currentDirection, it.blocks)
        }
    }

    /**
     * @param destinationX the relative X coordiante (relative to 0)
     * @param destinationY the relative Y coordinate (relative to 0)
     * @return the distance as defined by taxicab geometry (https://en.wikipedia.org/wiki/Taxicab_geometry)
     */
    private fun getDistanceTravelled(destinationX: Int, destinationY: Int): Int {
        val absCurrentX = Math.abs(destinationX)
        val absCurrentY = Math.abs(destinationY)
        val smallestAbsCoordinate = if (absCurrentX > absCurrentY) absCurrentY else absCurrentX
        val largestAbsCoordinate = if (absCurrentX > absCurrentY) absCurrentX else absCurrentY

        val singleAxisTravel = largestAbsCoordinate - smallestAbsCoordinate
        val diagonalTravel = smallestAbsCoordinate * 2

        return diagonalTravel + singleAxisTravel
    }

    enum class CardinalDirection {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        fun getLeftSideDirection() : CardinalDirection {
            val nextIndex = if (ordinal > 0) (ordinal - 1) else CardinalDirection.values().count() - 1
            return CardinalDirection.values()[nextIndex]
        }

        fun getRightSideDirection() : CardinalDirection {
            val nextIndex = if (ordinal < (CardinalDirection.values().count() - 1)) (ordinal + 1) else 0
            return CardinalDirection.values()[nextIndex]
        }
    }

    data class InputOperation(val direction: String, val blocks: Int)

    data class AbsoluteOperation(val cardinalDirection: CardinalDirection, val blocks: Int)
}