package net.kenvanhoeylandt.aoc2016.profiling

import java.util.*

/**
 * A simple profiler implementation that outputs the time when it is stopped.
 */
class Profiler() {
    val map: MutableMap<String, Long> = HashMap()

    fun start(tag: String) {
        val start = System.currentTimeMillis()
        map.put(tag, start)
    }

    fun stop(tag: String) {
        val stop = System.currentTimeMillis()
        val start = map.get(tag)

        if (start != null) {
            val duration = stop - start
            println(String.format("\"$tag\" took %.2f seconds", duration / 1000.0))
        }
    }
}