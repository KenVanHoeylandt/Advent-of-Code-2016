package net.kenvanhoeylandt.aoc2016.solutions.day6

import io.reactivex.Observable
import net.kenvanhoeylandt.aoc2016.Solution
import net.kenvanhoeylandt.aoc2016.rx.characterToStringBuilderCollector
import java.util.*

class Day6Solution : Solution(6) {

    override fun solvePartOne(input: String): String {
        // read input lines
        val observableInputLines = Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings

        // process each column and output the resulting string
        return processEachInputColumn(observableInputLines, false)
                .collectInto(StringBuilder(), characterToStringBuilderCollector())
                .blockingGet()
                .toString()
    }

    override fun solvePartTwo(input: String): String {
        // read input lines
        val observableInputLines = Observable.just(input)
                .flatMap { Observable.fromIterable(it.split("\n")) } // separate lines into separate Strings

        // process each column and output the resulting string
        return processEachInputColumn(observableInputLines, true)
                .collectInto(StringBuilder(), characterToStringBuilderCollector())
                .blockingGet()
                .toString()
    }

    // region private methods

    /**
     * Return an Observable that outputs the resulting character from each column
     */
    private fun processEachInputColumn(inputLines: Observable<String>, reverse: Boolean): Observable<Char> {
        // find out how many columns to read (word length)
        val wordLength = inputLines.firstElement().blockingGet().length
        val columnsToRead = wordLength - 1
        // iterate through each column and find the character for that column
        return Observable.concat<Char>((0..columnsToRead)
                .map { processCharacters(inputLines, it, reverse) })
    }

    /**
     * Return an Observable that returns the resulting character from the input data for a specific column
     */
    private fun processCharacters(inputLines: Observable<String>, index: Int, reverse: Boolean): Observable<Char> {
        return inputLines
                .map { it[index] }
                .collectInto(mutableMapOf<Char, Int>(), { map, character ->
                    val count = map.getOrElse(character) {0} + 1
                    map.put(character, count)
                })
                .flatMapObservable { Observable.fromIterable(it.entries) }
                .sorted(characterCountMapComparator(reverse))
                .map { it.key }
                .firstElement()
                .toObservable()
    }

    // endregion

    private fun characterCountMapComparator(reverse: Boolean): Comparator<MutableMap.MutableEntry<Char, Int>> {
        return Comparator { m1: MutableMap.MutableEntry<Char, Int>, m2: MutableMap.MutableEntry<Char, Int> ->
            if (reverse) Integer.compare(m1.value, m2.value) else Integer.compare(m2.value, m1.value)
        }
    }
}