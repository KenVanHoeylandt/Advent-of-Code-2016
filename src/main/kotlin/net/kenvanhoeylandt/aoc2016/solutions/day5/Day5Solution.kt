package net.kenvanhoeylandt.aoc2016.solutions.day5

import net.kenvanhoeylandt.aoc2016.Solution
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter


class Day5Solution : Solution(5) {

    override fun solvePartOne(input: String): String {
        var counter = 0 // counter for hashing
        val digits = StringBuilder() // storage for digits

        // find 8 digits
        while (digits.length < 8) {
            val hash = getHash(input, counter)
            // add the 6th digit of the hash when it is a valid hash
            if (isValidHash(hash)) {
                digits.append(hash[5])
            }

            counter++
        }

        return digits.toString().toLowerCase()
    }

    override fun solvePartTwo(input: String): String {
        var counter = 0 // counter for hashing
        val digits = StringBuilder("________") // underscore serves as a placeholder for a password digits

        // loop until all underscores are replaced
        while (digits.contains("_")) {
            val hash = getHash(input, counter)
            if (isValidHash(hash)) {
                val position = hash[5].toInt() - '0'.toInt()
                val character = hash[6]

                // when the position is valid and the digit is not set yet, set the digit
                if (position >= 0 && position <= 7 && digits[position] == '_') {
                    digits.setCharAt(position, character)
                }
            }

            counter++
        }

        return digits.toString().toLowerCase()
    }
}

fun getHash(input: String, counter: Int): String {
    val hashInput = input + counter.toString()
    val hash = getMd5(hashInput)
    return bytesToHex(hash)
}

fun getMd5(input: String): ByteArray {
    val md = MessageDigest.getInstance("MD5")
    return md.digest(input.toByteArray())
}

fun bytesToHex(bytes: ByteArray): String {
    return DatatypeConverter.printHexBinary(bytes)
}

fun isValidHash(hash: String): Boolean {
    return hash.startsWith("00000")
}