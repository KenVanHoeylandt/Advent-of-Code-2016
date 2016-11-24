package net.kenvanhoeylandt.aoc2016.services

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

/**
 * Get input to solve challenges.
 */
class ChallengeInputService(private val requestService: RequestService) {

    /**
     * Retrieve the assignment data for a specific day.
     * @param day the day to retrieve data for
     * *
     * @return the data as a String
     */
    fun request(day: Int): Single<String> {
        if (getLocalFile(day).exists()) {
            return getFromLocalFile(day)
        } else {
            return getFromWebsite(day)
        }
    }

    /**
     * Retrieve the assignment data for a specific day from a local file.
     * @param day the day to retrieve data for
     * *
     * @return the data as a String
     */
    private fun getFromLocalFile(day: Int): Single<String> {
        return Single.just(day)
                .observeOn(Schedulers.io())
                .map { getLocalFile(it) }
                .map(File::readBytes)
                .map { String(it) }
    }

    /**
     * Retrieve the assignment data for a specific day from the website.
     * @param day the day to retrieve data for
     * *
     * @return the data as a String
     */
    private fun getFromWebsite(day: Int): Single<String> {
        val request = requestService.requestBuilder()
                .url("http://adventofcode.com/2016/day/$day/input")
                .build()

        return requestService.executeForString(request)
                .map {
                    // Strip newline character
                    if (it.endsWith('\n')) {
                        it.substring(0, it.length - 1)
                    } else {
                        it
                    }
                }
                .doOnSuccess {
                    // Write data to local cache
                    val file = getLocalFile(day)

                    if (!file.exists()) {
                        file.createNewFile()
                    }

                    // Assignment data is not very big, so we can write it unbuffered
                    val outputStream = FileOutputStream(file)
                    outputStream.write(it.toByteArray())
                    outputStream.close()
                }
    }

    /**
     * Get the local file to store the local data.
     * The file might not exist yet.

     * @param day the day to retrieve File for
     * *
     * @return the file (which might not exist yet)
     */
    private fun getLocalFile(day: Int): File {
        val directory = File("data")

        if (!directory.isDirectory) {
            directory.mkdir()
        }

        return File(directory, "day$day")
    }
}