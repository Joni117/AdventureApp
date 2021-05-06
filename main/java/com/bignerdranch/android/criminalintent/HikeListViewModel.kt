package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel

class HikeListViewModel : ViewModel() {

    private val crimeRepository = HikeRepository.get()
    val hikeListLiveData = crimeRepository.getCrimes()

    fun addCrime(hike: Hike) {
        crimeRepository.addCrime(hike)
    }
}