package com.example.uas

import android.app.Application
import com.example.uas.repository.AppContainer
import com.example.uas.repository.PerpustakaanContainer

class PerpustakaanApplications: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = PerpustakaanContainer()
    }
}