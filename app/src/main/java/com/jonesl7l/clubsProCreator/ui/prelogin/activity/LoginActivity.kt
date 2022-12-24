package com.jonesl7l.clubsProCreator.ui.prelogin.activity

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jonesl7l.clubsProCreator.databinding.ActivityLoginBinding
import com.jonesl7l.clubsProCreator.ui.base.BaseActivity
import com.jonesl7l.clubsProCreator.util.showIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    //region Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //endregion

    //region GenericActivityCallback

    override fun shouldShowToolbar(shouldShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setToolbarTitle(title: String) {
        TODO("Not yet implemented")
    }

    override fun setLeftIcon(imageRef: Int, callback: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun showLeftIcon(shouldShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setRightIcon(imageRef: Int, callback: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun showRightIcon(shouldShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setMainButton(text: String, callback: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun showMainButton(shouldShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showLoader(shouldShow: Boolean) {
        binding.loginProgressLayout.showIf { shouldShow }
    }

    //endregion
}