package com.jonesl7l.clubsProCreator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonesl7l.clubsProCreator.data.LoginRepository
import com.jonesl7l.clubsProCreator.model.LoginFormState
import com.jonesl7l.clubsProCreator.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.jonesl7l.clubsProCreator.model.RestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private var _loginState: MutableStateFlow<LoginState?> = MutableStateFlow(LoginState.LOG_IN_IDLE)
    private var _loginFormState: MutableStateFlow<LoginFormState?> = MutableStateFlow(LoginFormState.USER_PASS_EMPTY)

    fun getLoginState(): StateFlow<LoginState?> = _loginState
    fun getLoginFormState(): StateFlow<LoginFormState?> = _loginFormState

    fun clearLoginState() {
        _loginState = MutableStateFlow(LoginState.LOG_IN_IDLE)
        _loginFormState = MutableStateFlow(LoginFormState.FORM_IDLE)
    }

    fun attemptUserAuthentication(email: String, password: String) {
        var error: LoginFormState? = null
        if (email.isBlank() || !email.matches(Regex(EMAIL_REGEX))) {
            error = LoginFormState.USER_INVALID_FORMAT
        }
        if (password.isBlank() || !password.matches(Regex(PASSWORD_REGEX))) {
            error = if (error == LoginFormState.USER_INVALID_FORMAT) {
                LoginFormState.USER_AND_PASS_INVALID_FORMAT
            } else {
                LoginFormState.PASS_INVALID_FORMAT
            }
        }
        if (error != null) {
            _loginFormState.value = error
            return
        }

        viewModelScope.launch {
            repository.authenticateUser(email, password).collect {
                when(it) {
                    is RestResult.Success -> {
                        _loginState.value = LoginState.LOGGED_IN
                    }
                    is RestResult.ApiError -> {
                        _loginState.value = LoginState.LOG_IN_FAILED
                    }
                    else -> {}
                }
            }
        }
    }

    fun attemptUserCreation(email: String, password: String) {
        var error: LoginFormState? = null
        if (email.isBlank() || !email.matches(Regex(EMAIL_REGEX))) {
            error = LoginFormState.USER_INVALID_FORMAT
        }
        if (password.isBlank() || !password.matches(Regex(PASSWORD_REGEX))) {
            error = if (error == LoginFormState.USER_INVALID_FORMAT) {
                LoginFormState.USER_AND_PASS_INVALID_FORMAT
            } else {
                LoginFormState.PASS_INVALID_FORMAT
            }
        }
        if (error != null) {
            _loginFormState.value = error
            return
        }

        viewModelScope.launch {
            repository.createUserWithEmail(email, password).collect {
                 when(it) {
                    is RestResult.Success -> {
                        _loginState.value = LoginState.LOGGED_IN
                    }
                    is RestResult.ApiError -> {
                        _loginState.value = LoginState.LOG_IN_FAILED
                    }
                     else -> {}
                 }
            }
        }
    }

    fun attemptForgotPassword(email: String) {
        var error: LoginFormState? = null
        if (email.isBlank() || !email.matches(Regex(EMAIL_REGEX))) {
            error = LoginFormState.USER_INVALID_FORMAT
        }
        if (error != null) {
            _loginFormState.value = error
            return
        }

        viewModelScope.launch {
            repository.resetPassword(email).collect {
                when(it) {
                    is RestResult.Success -> {
                        _loginState.value = LoginState.LOGGED_IN
                    }
                    is RestResult.ApiError -> {
                        _loginState.value = LoginState.LOG_IN_FAILED
                    }
                    else -> {}
                }
            }
        }
    }

    fun signOut() {
        repository.signOut()
        _loginState.value = LoginState.LOGGED_OUT
    }

    companion object {
        private const val EMAIL_REGEX =
            "^[a-zA-Z0-9]+([\\w.'!#$%&*+\\-/=?^`{|}~])*([a-zA-Z0-9])+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}\$"
        private const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$"
        private const val NAME_REGEX = "^[a-zA-Z ,.'-]+\$"
    }
}