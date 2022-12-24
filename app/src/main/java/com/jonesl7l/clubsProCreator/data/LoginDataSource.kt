package com.jonesl7l.clubsProCreator.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jonesl7l.clubsProCreator.model.RestResult
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class LoginDataSource @Inject constructor() {

    private val mFirebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private var currentUser: FirebaseUser? = null

    init {
        currentUser = mFirebaseAuth.currentUser
        if (currentUser != null) {
//            reload()
        }
    }

    suspend fun createUserWithEmail(email: String, password: String): RestResult<AuthResult?> {
        return try {
            val data = mFirebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            RestResult.Success(data)
        } catch (exception: Exception) {
            Timber.e(exception)
            RestResult.ApiError(exception.message.orEmpty())
        }
    }

    suspend fun authenticateUser(email: String, password: String): RestResult<AuthResult?> {
        return try {
            val data = mFirebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            RestResult.Success(data)
        } catch (exception: Exception) {
            Timber.e(exception)
            RestResult.ApiError(exception.message.orEmpty(), exception)
        }
    }

    suspend fun resetPassword(email: String): RestResult<Any?> {
        return try {
            val data = mFirebaseAuth
                .sendPasswordResetEmail(email)
                .await()
            RestResult.Success(data)
        } catch (exception: Exception) {
            Timber.e(exception)
            RestResult.ApiError(exception.message.orEmpty())
        }
    }

    fun signOut() {
        currentUser = null
        mFirebaseAuth.signOut()
    }
}