package com.practice.teachmint_internship_jetpack.data.model

data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    val projectLink: String,
    val owner: String
)
