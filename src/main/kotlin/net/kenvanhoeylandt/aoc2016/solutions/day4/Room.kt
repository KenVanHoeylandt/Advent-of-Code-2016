package net.kenvanhoeylandt.aoc2016.solutions.day4

import java.util.regex.Pattern
import kotlin.comparisons.compareBy

data class Room(val name: String, val sectorId: Int, val checksum: String) {
    fun isValid(): Boolean {
        val cleanedName = name.replace("-", "")
        val characterMap = mutableMapOf<Char, Int>()
        for (char in cleanedName) {
            val count = characterMap.getOrElse(char) { 0 } + 1
            characterMap.put(char, count)
        }

        val nameChecksumParts = characterMap.entries
                .sortedWith(compareBy({ -it.value }, { it.key }))
                .map { it.key }
                .subList(0, 5)

        val nameChecksum = StringBuilder()

        nameChecksumParts.forEach {
            nameChecksum.append(it)
        }

        return nameChecksum.toString() == checksum
    }
}

fun roomOf(text: String): Room {
    val pattern = Pattern.compile("([a-z0-9-]+)-([0-9]+)\\[([a-z]+)\\]")
    val matcher = pattern.matcher(text)

    if (matcher.groupCount() != 3) throw RuntimeException("wrong amount of groups in room input")
    if (!matcher.matches()) throw RuntimeException("no match")

    val name = matcher.group(1)
    val sectorId = matcher.group(2).toInt()
    val checksum = matcher.group(3)

    return Room(name, sectorId, checksum)
}