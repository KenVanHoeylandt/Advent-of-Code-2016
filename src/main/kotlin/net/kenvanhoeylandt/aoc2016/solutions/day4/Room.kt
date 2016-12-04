package net.kenvanhoeylandt.aoc2016.solutions.day4

import java.util.regex.Pattern
import kotlin.comparisons.compareBy

/**
 * A room as described in the assignment.
 */
data class Room(val name: String, val sectorId: Int, val checksum: String) {

    /**
     * Validity check for Part 1
     */
    fun isValid(): Boolean {
        // We're not interested in dashes
        val cleanedName = name.replace("-", "")
        // Find out how many times a character is used
        val characterMap = mutableMapOf<Char, Int>()
        for (char in cleanedName) {
            val count = characterMap.getOrElse(char) { 0 } + 1
            characterMap.put(char, count)
        }

        // Sort the character map entries: first by amount of times seen (value, from large to small),
        // then by character (alphabetically, from small to large)
        val nameChecksumParts = characterMap.entries
                .sortedWith(compareBy({ -it.value }, { it.key }))
                .map { it.key }
                .subList(0, 5)

        // Gather all characters into a String
        val nameChecksum = StringBuilder()
        nameChecksumParts.forEach {
            nameChecksum.append(it)
        }

        return nameChecksum.toString() == checksum
    }

    /**
     * Shift cipher decryption for Part 2
     */
    fun decrypt(): String {
        val cleanedName = name.replace("-", " ")
        val decrypted = StringBuilder()

        // go through each character
        for (character in cleanedName) {
            if (character != ' ') {
                // ignore whole alphabet cycles
                val remainder = sectorId % 26
                val newCharacter = character + remainder
                // ensure we have a valid character still
                if (newCharacter > 'z') {
                    decrypted.append(newCharacter - 26)
                } else {
                    decrypted.append(newCharacter)
                }
            } else {
                decrypted.append(character)
            }
        }

        return decrypted.toString()
    }
}

/**
 * Convert input text into a Room instance
 */
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