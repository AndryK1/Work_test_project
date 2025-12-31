package com.practicum.work_test_project.data.search.network.interseptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

//перехватываем запросы тут
class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()

        return when {

            url.contains("/courses") -> {
                try {

                    val jsonString = context.assets.open("mock_courses")
                        .bufferedReader().use { it.readText() }

                    Response.Builder()
                        .code(200)
                        .message("OK")
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .body(jsonString.toResponseBody("application/json".toMediaType()))
                        .build()
                } catch (e: IOException) {
                    Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(404)
                        .message("Mock file not found")
                        .body("{}".toResponseBody("application/json".toMediaType()))
                        .build()
                }
            }
            else -> {
                Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(400)
                    .message("Mock: Unknown endpoint")
                    .body("{}".toResponseBody("application/json".toMediaType()))
                    .build()
            }
        }
    }
}