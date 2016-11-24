package net.kenvanhoeylandt.aoc2016.services

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookieStore
import java.nio.charset.Charset

/**
 * Generic service to create and execute HTTP requests through the OkHttp library.
 */
class RequestService() {
    private val client: OkHttpClient = OkHttpClient()
    private val cookieManager = CookieManager()

    init {
        CookieHandler.setDefault(cookieManager)
        cookieManager.setCookiePolicy(java.net.CookiePolicy.ACCEPT_ALL)
        client.cookieHandler = cookieManager
    }

    val cookieStore: CookieStore
        get() = cookieManager.cookieStore

    fun requestBuilder(): Request.Builder {
        return Request.Builder()
    }

    fun execute(request: Request): Single<Response> {
        return Single.just(request)
                .observeOn(Schedulers.io())
                .map { client.newCall(it).execute() }
    }

    fun executeForString(request: Request): Single<String> {
        return execute(request)
                .map { it.body().source().readString(Charset.forName("UTF-8")) }
    }
}
