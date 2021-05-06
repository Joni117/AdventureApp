package com.bignerdranch.android.criminalintent

import android.app.Application

class AdventureApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HikeRepository.initialize(this)
    }
}