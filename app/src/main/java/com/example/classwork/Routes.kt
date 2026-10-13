package com.example.classwork

import kotlinx.serialization.Serializable

@Serializable
object Cats

@Serializable
data class CatInfo(val name: String, val image: String, val info: String)