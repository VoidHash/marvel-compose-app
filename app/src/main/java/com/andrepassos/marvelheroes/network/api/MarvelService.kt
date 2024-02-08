package com.andrepassos.marvelheroes.network.api

import com.andrepassos.marvelheroes.BuildConfig
import com.andrepassos.marvelheroes.util.DataUtil
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarvelService {

    private const val BASE_URL = "http://gateway.marvel.com/v1/public/"

    private fun getRetrofit(): Retrofit {
        val timeStamp = System.currentTimeMillis().toString()
        val apiSecret = BuildConfig.MARVEL_SECRET
        val apiKey = BuildConfig.MARVEL_KEY
        val hash = DataUtil.getHash(timeStamp, apiSecret, apiKey)

        val headerInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("ts", timeStamp)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()
            request = request.newBuilder().url(url).build()

            chain.proceed(request)
        }

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(logInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi::class.java)
}