package net.kenvanhoeylandt.aoc2016

/**
 * Abstract base class for a solution.
 */
abstract class Solution(val day: Int) {

    @Throws(Exception::class)
    abstract fun solve(input: String): String
}