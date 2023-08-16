package com.example.e_commerce.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.koin.dsl.module

val dbModule = module {
    single {
        FirebaseDatabase.getInstance()
    }

    single {
        FirebaseStorage.getInstance()
    }
}
