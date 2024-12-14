package com.example.graphapp.data.api

import com.example.graphapp.data.entity.response.PointsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/test/points")
    suspend fun getPoints(@Query("count") count: Int): Response<PointsResponse>
}