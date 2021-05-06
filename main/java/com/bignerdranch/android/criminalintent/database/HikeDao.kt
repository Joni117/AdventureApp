package com.bignerdranch.android.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.criminalintent.Hike
import java.util.*

@Dao
interface HikeDao {

    @Query("SELECT * FROM hike")
    fun getHike(): LiveData<List<Hike>>

    @Query("SELECT * FROM hike WHERE id=(:id)")
    fun getHike(id: UUID): LiveData<Hike?>

    @Update
    fun updateHike(hike: Hike)

    @Insert
    fun addHike(hike: Hike)
}