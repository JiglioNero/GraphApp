package com.example.graphapp.data.repository

import com.example.graphapp.data.api.ApiService
import com.example.graphapp.data.entity.Point

class PointsRepository(private val apiService: ApiService) {
    suspend fun fetchPoints(count: Int): Result<List<Point>> {
        return try {
            val response = apiService.getPoints(count)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.points)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Server error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
