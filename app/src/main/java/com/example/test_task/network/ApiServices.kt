package com.example.test_task.network

import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("/")
    fun getData(@QueryMap params: HashMap<String, String>): Call<JsonElement>

}
