package com.practice.teachmint_internship_jetpack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val projectLink: String,
    val owner: String
)
