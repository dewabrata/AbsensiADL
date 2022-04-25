package com.adl.absensiadl.services

import com.adl.absensiadl.repository.ResponseLogin
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig
{

    fun getInterceptor():OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return okHttpClient


    }

    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.114/cicool/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAbsen() = getRetrofit().create(ILogin::class.java)



}