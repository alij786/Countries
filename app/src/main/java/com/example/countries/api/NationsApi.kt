package com.example.countries.api

import android.util.Log
import com.example.countries.entities.Nations
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface NationsApi {
    @GET("nations.json")
    suspend fun getNations(): Response<Nations>

    companion object {
        private const val BASE_URL =
            "https://example.com/nations/"

        val instance: NationsApi by lazy {
            val gson = GsonBuilder().create()

            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor { message ->
                        Log.v("NationsApi", message)
                    }.setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(NationsApi::class.java)
        }
    }
}
