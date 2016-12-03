package net.kenvanhoeylandt.aoc2016.solutions.day2

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution

class Day2Solution : Solution(2) {

    @Throws(Exception::class)
    override fun solvePartOne(input: String): String {
        return Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings
                .map(String::toCharArray) // convert each line from String to CharArray
                .map(::directionOf) // convert each CharArray into List<Direction>
                .map(::getNumberFromDirectionsPartOne)
                .collectInto(StringBuilder(), { stringBuilder: StringBuilder, s: Char -> stringBuilder.append(s) })
                .blockingGet()
                .toString()
    }

    @Throws(Exception::class)
    override fun solvePartTwo(input: String): String {
        return Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings
                .map(String::toCharArray) // convert each line from String to CharArray
                .map(::directionOf) // convert each CharArray into List<Direction>
                .map(::getNumberFromDirectionsPartTwo)
                .collectInto(StringBuilder(), { stringBuilder: StringBuilder, s: Char -> stringBuilder.append(s) })
                .blockingGet()
                .toString()
    }
}
