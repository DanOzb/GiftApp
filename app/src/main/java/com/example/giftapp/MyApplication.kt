package com.example.giftapp

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        if(FirebaseAuth.getInstance().currentUser == null){
            FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){

                    } else {
                        task.exception?.printStackTrace()
                    }
                }
        }
    }

}