package com.example.graphapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(val x: Float, val y: Float) : Parcelable
