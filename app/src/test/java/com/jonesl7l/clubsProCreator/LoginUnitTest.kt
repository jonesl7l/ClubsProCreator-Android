package com.jonesl7l.clubsProCreator

import com.jonesl7l.clubsProCreator.data.LoginDataSource
import com.jonesl7l.clubsProCreator.model.RestResult
import org.junit.Test

class LoginUnitTest {

    private var loginDataSource: LoginDataSource = LoginDataSource()

    @Test
    suspend fun createAccountTest() {
        val result = loginDataSource.createUserWithEmail(TEST_EMAIL, TEST_PASSWORD)
        assert(result is RestResult.Success)
    }

    @Test
    fun loginAccountTest() {
    }

    @Test
    fun forgetPasswordTest() {
    }

    companion object {
        private const val TEST_EMAIL = "test123@hotmail.com" //Change me if re-running all tests
        private const val TEST_PASSWORD = "Password+123"
    }
}