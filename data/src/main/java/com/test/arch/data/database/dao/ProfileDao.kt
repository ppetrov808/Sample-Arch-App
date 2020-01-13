package com.test.arch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.arch.data.database.model.UserProfileDbEntity
import io.reactivex.Maybe

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(profile: UserProfileDbEntity)

    @Query("select * from profile LIMIT 1")
    fun getProfile(): Maybe<UserProfileDbEntity>

    @Query("DELETE FROM profile")
    fun clearProfile()
}