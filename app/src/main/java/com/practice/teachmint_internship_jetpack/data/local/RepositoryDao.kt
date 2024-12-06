package com.practice.teachmint_internship_jetpack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM RepositoryEntity")
    fun getAllRepositories(): Flowable<List<RepositoryEntity>>
}
