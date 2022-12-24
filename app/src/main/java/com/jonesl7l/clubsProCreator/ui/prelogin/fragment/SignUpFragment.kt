package com.jonesl7l.clubsProCreator.ui.prelogin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext
import com.jonesl7l.clubsProCreator.databinding.FragmentLoginBinding
import com.jonesl7l.clubsProCreator.model.LoginFormState
import com.jonesl7l.clubsProCreator.model.LoginState
import com.jonesl7l.clubsProCreator.ui.base.BaseFragment
import com.jonesl7l.clubsProCreator.util.dismissKeyboard
import com.jonesl7l.clubsProCreator.util.notImplemented
import com.jonesl7l.clubsProCreator.util.then
import com.jonesl7l.clubsProCreator.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    //region Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    //endregion

    //region BaseFragment

    override fun initFragment() {
        binding.loginAnimation.repeatCount = LottieDrawable.INFINITE
        binding.btnSignIn.setOnClickListener {
            activity?.dismissKeyboard()
            viewModel.attemptUserCreation(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    override fun observeLiveData() {
        lifecycleScope.launch {
            launch {
                viewModel.getLoginState().collect { loginResult ->
                    when (loginResult) {
                        LoginState.SIGN_UP_SUCCESS -> onSignupSuccess()
                        LoginState.SIGN_UP_LOADING -> onSignupLoading()
                        LoginState.SIGN_UP_FAILED -> onSignupError()
                        else -> {
                            //Not required
                        }
                    }
                }
            }
            launch {
                viewModel.getLoginFormState().collect { loginFormResult ->
                    when (loginFormResult) {
                        LoginFormState.FORM_IDLE -> {
                            handleEmailInputError(false)
                            handlePasswordInputError(false)
                        }
                        LoginFormState.USER_PASS_EMPTY -> {
                            handleEmailInputError(false)
                            handlePasswordInputError(false)
                            handleEmptyFormDialog()
                        }
                        LoginFormState.USER_INVALID_FORMAT -> {
                            handleEmailInputError(true)
                            handlePasswordInputError(false)
                        }
                        LoginFormState.PASS_INVALID_FORMAT -> {
                            handleEmailInputError(true)
                            handlePasswordInputError(true)
                        }
                        LoginFormState.USER_AND_PASS_INVALID_FORMAT -> {
                            handleEmailInputError(false)
                            handlePasswordInputError(true)
                        }
                        else -> {
                            //Not required
                        }
                    }
                }
            }
        }
    }

    //endregion

    //region Ui

    private fun onSignupLoading() {
        binding.loginAnimation.playAnimation()
        binding.btnSignIn.isEnabled = false
        binding.tilEmail.error = ""
        binding.tilPassword.error = ""
    }

    private fun onSignupSuccess() {
        binding.loginAnimation.cancelAnimation()
        binding.loginAnimation.frame = 0
        binding.btnSignIn.isEnabled = true
        viewModel.clearLoginState()
        findNavController().navigate(SignUpFragmentDirections.actionSignupFragmentToLoginFragment(binding.etEmail.text.toString()))
    }

    private fun onSignupError() {
        binding.loginAnimation.cancelAnimation()
        binding.loginAnimation.frame = 0
        binding.btnSignIn.isEnabled = true
        handleSignupErrorDialog()
    }

    private fun handleSignupErrorDialog() {
        notImplemented()
    }

    private fun handleEmptyFormDialog() {
        notImplemented()
    }

    private fun handleEmailInputError(shouldShow: Boolean) {
        binding.tilEmail.error = (shouldShow then appContext.getString(R.string.email_address_error)) ?: ""
    }

    private fun handlePasswordInputError(shouldShow: Boolean) {
        binding.tilPassword.error = (shouldShow then appContext.getString(R.string.password_error)) ?: ""
    }

    //endregion
}