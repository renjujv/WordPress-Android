package org.wordpress.android.modules

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton

@Singleton
class MockHostInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url().host() == "public-api.wordpress.com") {
            val newUrl = request.url().newBuilder()
                .host("do.wpmt.co")
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .build()
            return chain.proceed(newRequest)
        }

        return chain.proceed(request)
    }
}
