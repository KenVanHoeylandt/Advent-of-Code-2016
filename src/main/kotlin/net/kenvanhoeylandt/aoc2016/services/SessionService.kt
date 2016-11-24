package net.kenvanhoeylandt.aoc2016.services

import java.net.HttpCookie
import java.net.URI
import java.net.URISyntaxException

/**
 * Manages the session token for authentication.
 */
class SessionService(private val requestService: RequestService) {

    fun setSessionToken(sessionToken: String) {
        val cookie = HttpCookie("session", sessionToken)
        cookie.path = "/"
        cookie.version = 0
        cookie.domain = "adventofcode.com"

        try {
            requestService.cookieStore.add(URI("http://adventofcode.com"), cookie)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
}
