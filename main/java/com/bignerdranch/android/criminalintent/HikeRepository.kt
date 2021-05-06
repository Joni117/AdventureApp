package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.criminalintent.database.CrimeDatabase
import com.bignerdranch.android.criminalintent.database.migration_1_2
import java.io.File
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "hike-database"

class HikeRepository private constructor(context: Context) {

    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()
    private val hikeDao = database.hikeDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir

    fun getCrimes(): LiveData<List<Hike>> = hikeDao.getHike()

    fun getHike(id: UUID): LiveData<Hike?> = hikeDao.getHike(id)

    fun updateHike(hike: Hike) {
        executor.execute {
            hikeDao.updateHike(hike)
        }
    }

    fun addCrime(hike: Hike) {
        executor.execute {
            hikeDao.addHike(hike)
        }
    }

    fun getPhotoFile(hike: Hike): File = File(filesDir, hike.photoFileName)
    
    companion object {
        private var INSTANCE: HikeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = HikeRepository(context)
            }
        }

        fun get(): HikeRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}