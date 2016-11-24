package net.kenvanhoeylandt.aoc2016

import io.reactivex.Single
import net.kenvanhoeylandt.aoc2016.profiling.Profiler
import net.kenvanhoeylandt.aoc2016.services.ChallengeInputService
import net.kenvanhoeylandt.aoc2016.services.RequestService
import net.kenvanhoeylandt.aoc2016.services.SessionService
import net.kenvanhoeylandt.aoc2016.solutions.Day1Solution

open class Application {
    companion object {
        private val solutions = arrayOf<Solution>(Day1Solution())
        private val requestService = RequestService()
        private val sessionService = SessionService(requestService)
        private val challengeInputService = ChallengeInputService(requestService)
        private val profiler = Profiler()

        @JvmStatic
        fun main(args: Array<String>) {

            println("Advent of Code solutions by Ken Van Hoeylandt")
            println()

            try {
                val applicationParemeters = applicationParameters(args)

                sessionService.setSessionToken(applicationParemeters.sessionToken)

                val dayIndex = applicationParemeters.day - 1

                if (dayIndex < 0 || dayIndex > solutions.lastIndex) {
                    println("ERROR: Failed to get solution")
                    return
                }

                val solution = solutions[dayIndex]

                solve(solution)
                        .toFuture()
                        .get() // wait to complete

            } catch (caught: Exception) {
                println("Usage:")
                println("\tjava -jar AdventOfCode2016.jar [sessionToken] [day]")
            }
        }

        private fun solve(solution: Solution): Single<String> {
            println("Retrieving assignment data...")

            return challengeInputService.request(solution.day)
                    .map {
                        println("Solving assignment...")
                        profiler.start("Solution")
                        val result = solution.solve(it)
                        profiler.stop("Solution")
                        result
                    }
                    .doOnError {
                        println("error when solving assignment")

                        val message = it.message

                        if (message != null) {
                            println("Solution failed: $message")
                        }

                        it.printStackTrace()
                    }
                    .doOnSuccess {
                        println("Solution for day ${solution.day}: $it")
                    }
        }
    }
}