package dev.dmie.justdoit.service.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides fun auth(): FirebaseAuth = Firebase.auth

    @Provides fun firestore(): FirebaseFirestore = Firebase.firestore

    @Provides fun crashlytics(): FirebaseCrashlytics = Firebase.crashlytics
}
