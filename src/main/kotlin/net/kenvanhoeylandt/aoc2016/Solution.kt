package net.kenvanhoeylandt.aoc2016

/**
 * Abstract base class for a solution.
 */
abstract class Solution(val day: Int) {

    abstract fun solvePartOne(input: String): String

    abstract fun solvePartTwo(input: String): String
}