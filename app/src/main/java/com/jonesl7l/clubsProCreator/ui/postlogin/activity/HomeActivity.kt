package com.jonesl7l.clubsProCreator.ui.postlogin.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.jonesl7l.clubsProCreator.databinding.ActivityHomeBinding
import com.jonesl7l.clubsProCreator.ui.base.BaseActivity
import com.jonesl7l.clubsProCreator.ui.base.GenericActivityCallback
import com.jonesl7l.clubsProCreator.util.showIf
import com.jonesl7l.clubsProCreator.viewmodel.VirtualProGlobalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity: BaseActivity(), GenericActivityCallback {

    private val viewModel: VirtualProGlobalViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    //region Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.fetchGlobalProStats()
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
        binding.homeProgressLayout.showIf { shouldShow }
    }

    override fun getVirtualProGlobalViewModel(): VirtualProGlobalViewModel = viewModel

    //endregion
}