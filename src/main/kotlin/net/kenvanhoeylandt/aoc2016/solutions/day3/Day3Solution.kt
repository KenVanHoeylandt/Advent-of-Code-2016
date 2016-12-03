package net.kenvanhoeylandt.aoc2016.solutions.day3

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution

class Day3Solution : Solution(3) {

    @Throws(Exception::class)
    override fun solvePartOne(input: String): String {
        return Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings
                .map(::triangleSidesOf)
                .filter(TriangleSides::isValid)
                .count()
                .blockingGet()
                .toString()
    }

    @Throws(Exception::class)
    override fun solvePartTwo(input: String): String {
        return "nothing"
    }
}