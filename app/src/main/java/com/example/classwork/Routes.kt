package com.example.classwork

import kotlinx.serialization.Serializable

@Serializable
object First

@Serializable
data class Second(val email: String, val password: String)