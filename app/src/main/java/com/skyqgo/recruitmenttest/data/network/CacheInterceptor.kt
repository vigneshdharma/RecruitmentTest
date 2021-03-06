package com.skyqgo.recruitmenttest.data.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
        if (!shouldUseCache) return originalResponse

        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.MINUTES)   //The data must be cached for 10 minutes
            .build()

        return originalResponse.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }

    companion object {
        private const val CACHE_CONTROL_HEADER = "Cache-Control"
        private const val CACHE_CONTROL_NO_CACHE = "no-cache"
    }
}