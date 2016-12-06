package net.kenvanhoeylandt.aoc2016.solutions.day1

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution
import net.kenvanhoeylandt.aoc2016.geometry.MutablePosition2D

class Day1Solution : Solution(1) {

    override fun solvePartOne(input: String): String {
        // read input
        val inputOperations = readInput(input)
        // convert to absolute movements
        val absoluteOperations = getAbsoluteOperations(inputOperations)

        val distanceTravelled = Observable.fromIterable(absoluteOperations)
                .collectInto(MutablePosition2D(0, 0), { position: MutablePosition2D, operation: AbsoluteOperation ->
                    when (operation.cardinalDirection) {
                        CardinalDirection.NORTH -> position.y -= operation.blocks
                        CardinalDirection.EAST -> position.x += operation.blocks
                        CardinalDirection.SOUTH -> position.y += operation.blocks
                        CardinalDirection.WEST -> position.x -= operation.blocks
                    }
                })
                .map{getDistanceTravelled(it)}
                .blockingGet()

        return "travelled $distanceTravelled blocks"
    }

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
     * @param position the 2d position
     * @return the distance as defined by taxicab geometry (https://en.wikipedia.org/wiki/Taxicab_geometry)
     */
    private fun getDistanceTravelled(position: MutablePosition2D): Int {
        return getDistanceTravelled(position.x, position.y)
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
}