package com.jonesl7l.clubsProCreator.data

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton
import com.jonesl7l.clubsProCreator.model.RestResult

@Singleton
class LoginRepository @Inject constructor(private val dataSource: LoginDataSource) {

    suspend fun createUserWithEmail(email: String, password: String): Flow<RestResult<AuthResult?>> {
        return flow {
            emit(RestResult.Loading)
            val result = dataSource.createUserWithEmail(email, password)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun authenticateUser(email: String, password: String): Flow<RestResult<AuthResult?>> {
        return flow {
            emit(RestResult.Loading)
            val result = dataSource.authenticateUser(email, password)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun resetPassword(email: String): Flow<RestResult<Any?>> {
        return flow {
            emit(RestResult.Loading)
            val result = dataSource.resetPassword(email)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun signOut() {
        dataSource.signOut()
    }
}