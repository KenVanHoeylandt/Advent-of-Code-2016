package net.kenvanhoeylandt.aoc2016.solutions.day3

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution

class Day3Solution : Solution(3) {

    override fun solvePartOne(input: String): String {
        return Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings
                .map(::triangleSidesOf)
                .filter(TriangleSides::isValid)
                .count()
                .blockingGet()
                .toString()
    }

    override fun solvePartTwo(input: String): String {
        // Get the TriangleSides objects as with part one
        val observableTriangleSidesFromPartOne = Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings
                .map(::triangleSidesOf)

        // Convert TriangleSides instances to column data (list of integers that represent the column)
        val columnOne = observableTriangleSidesFromPartOne.map{ it.a }
        val columnTwo = observableTriangleSidesFromPartOne.map{ it.b }
        val columnThree = observableTriangleSidesFromPartOne.map{ it.c }

        // Get a single list from the 3 columns
        val numberList = Observable.concat(columnOne, columnTwo, columnThree)
                .toList()
                .blockingGet()

        // Create TriangleSides again from the column input data
        val maxIndex = (numberList.size/3) - 1
        val triangleSidesList = (0..maxIndex).map {
            val index = it * 3
            TriangleSides(numberList[index], numberList[index+1], numberList[index+2])
        }

        // Count valid TriangleSides instances
        return Observable.fromIterable(triangleSidesList)
                .filter(TriangleSides::isValid)
                .count()
                .blockingGet()
                .toString()
    }
}