package net.kenvanhoeylandt.aoc2016.solutions.day4

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution
import sun.java2d.xr.MutableInteger

class Day4Solution : Solution(4) {

    @Throws(Exception::class)
    override fun solvePartOne(input: String): String {
        return Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings
                .map(::roomOf) // convert input to Room instances
                .filter(Room::isValid) // skip invalid rooms
                .map(Room::sectorId) // get all sector IDs
                .collectInto(MutableInteger(0), { mutableInteger: MutableInteger, i: Int -> mutableInteger.value += i }) // sum of sector IDs
                .map { it.value } // get the MutableInteger value
                .blockingGet()
                .toString()
    }

    @Throws(Exception::class)
    override fun solvePartTwo(input: String): String {
        return "nothing"
    }
}