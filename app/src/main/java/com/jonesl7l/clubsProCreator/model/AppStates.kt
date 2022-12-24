package com.jonesl7l.clubsProCreator.model

enum class LoginState {

    //Login
    LOG_IN_IDLE,
    LOGGED_IN,
    LOGGING_IN,
    LOG_IN_FAILED,
    LOGGED_OUT,

    //Forgot Password
    FORGOT_PASSWORD_IDLE,
    FORGOT_PASSWORD_LOADING,
    FORGOT_PASSWORD_SUCCESS,
    FORGOT_PASSWORD_FAILED,

    //Signup
    SIGN_UP_IDLE,
    SIGN_UP_LOADING,
    SIGN_UP_SUCCESS,
    SIGN_UP_FAILED
}

enum class LoginFormState {
    FORM_IDLE,
    USER_PASS_EMPTY,
    USER_INVALID_FORMAT,
    PASS_INVALID_FORMAT,
    USER_AND_PASS_INVALID_FORMAT,
}