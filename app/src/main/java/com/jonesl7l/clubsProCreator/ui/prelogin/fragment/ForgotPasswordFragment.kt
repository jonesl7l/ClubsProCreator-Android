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
import com.jonesl7l.clubsProCreator.databinding.FragmentForgotPasswordBinding
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
class ForgotPasswordFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordBinding

    //region Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForgotPasswordBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    //endregion

    //region BaseFragment

    override fun initFragment() {
        binding.forgotPasswordAnimation.repeatCount = LottieDrawable.INFINITE
        binding.btnSignIn.setOnClickListener {
            activity?.dismissKeyboard()
            viewModel.attemptForgotPassword(binding.etEmail.text.toString())
        }
    }

    override fun observeLiveData() {
        lifecycleScope.launch {
            launch {
                viewModel.getLoginState().collect { forgotPasswordResult ->
                    when (forgotPasswordResult) {
                        LoginState.FORGOT_PASSWORD_SUCCESS -> onForgotPasswordSuccess()
                        LoginState.FORGOT_PASSWORD_LOADING -> onForgotPasswordLoading()
                        LoginState.FORGOT_PASSWORD_FAILED -> onForgotPasswordError()
                        else -> {
                            //Not required
                        }
                    }
                }
            }
            launch {
                viewModel.getLoginFormState().collect { forgotPasswordFormResult ->
                    when (forgotPasswordFormResult) {
                        LoginFormState.FORM_IDLE -> {
                            handleEmailInputError(false)
                        }
                        LoginFormState.USER_PASS_EMPTY -> {
                            handleEmailInputError(false)
                            handleEmptyFormDialog()
                        }
                        LoginFormState.USER_INVALID_FORMAT -> {
                            handleEmailInputError(true)
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

    private fun onForgotPasswordLoading() {
        binding.forgotPasswordAnimation.playAnimation()
        binding.btnSignIn.isEnabled = false
        binding.tilEmail.error = ""
    }

    private fun onForgotPasswordSuccess() {
        binding.forgotPasswordAnimation.cancelAnimation()
        binding.forgotPasswordAnimation.frame = 0
        binding.btnSignIn.isEnabled = true
        viewModel.clearLoginState()
        findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment(binding.etEmail.text.toString()))
    }

    private fun onForgotPasswordError() {
        binding.forgotPasswordAnimation.cancelAnimation()
        binding.forgotPasswordAnimation.frame = 0
        binding.btnSignIn.isEnabled = true
        handleForgotPasswordErrorDialog()
    }

    private fun handleForgotPasswordErrorDialog() {
        notImplemented()
    }

    private fun handleEmptyFormDialog() {
        notImplemented()
    }

    private fun handleEmailInputError(shouldShow: Boolean) {
        binding.tilEmail.error = (shouldShow then appContext.getString(R.string.email_address_error)) ?: ""
    }

    //endregion
}