package com.example.projectakhir

import android.app.Application
import com.example.projectakhir.dependenciesinjection.AppContainer
import com.example.projectakhir.dependenciesinjection.AppContainerImpl


class EventApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Menggunakan AppContainerImpl untuk mengelola semua repository
        container = AppContainerImpl()
    }
}