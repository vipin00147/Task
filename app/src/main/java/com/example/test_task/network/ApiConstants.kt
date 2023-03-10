package com.example.test_task.network

import androidx.viewbinding.ViewBinding
import com.google.gson.GsonBuilder
import com.example.test_task.base.BaseActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConstants {

    private var requestTimeout = 60
    private var context : BaseActivity<ViewBinding>?= null

    private var retrofit : Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    private const val BASE_URL = "http://www.omdbapi.com/"
    const val API_KEY = "b9bd48a6"

    // Create Service
    private val service = getClient(BASE_URL, false).create(ApiServices::class.java)

    fun initContext(mContext: BaseActivity<ViewBinding>) {
        context = mContext
    }
    fun getApiServices(): ApiServices {
        return service
    }

    // Create Retrofit Client
    private fun getClient(baseUrl: String, initializeAgain : Boolean): Retrofit {

        if (okHttpClient == null)
            initOkHttp()

        val gson = GsonBuilder().setLenient().create()
        if (retrofit == null || initializeAgain) {

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
        }

        return retrofit!!
    }

    private fun initOkHttp() {

        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(requestTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(requestTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(requestTimeout.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}