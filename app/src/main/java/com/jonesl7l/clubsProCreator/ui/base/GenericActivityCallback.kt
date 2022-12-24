package com.jonesl7l.clubsProCreator.ui.base

import com.jonesl7l.clubsProCreator.viewmodel.VirtualProGlobalViewModel

interface GenericActivityCallback {

    //region Toolbar

    fun shouldShowToolbar(shouldShow: Boolean = true)
    fun setToolbarTitle(title: String = "")
    fun setLeftIcon(imageRef: Int = 0, callback: (() -> Unit)? = null)
    fun showLeftIcon(shouldShow: Boolean)
    fun setRightIcon(imageRef: Int = 0, callback: (() -> Unit)? = null)
    fun showRightIcon(shouldShow: Boolean)

    //endregion

    //region Main Button

    fun setMainButton(text: String = "", callback: (() -> Unit)? = null)
    fun showMainButton(shouldShow: Boolean = false)

    //endregion

    fun showLoader(shouldShow: Boolean = true)

    fun getVirtualProGlobalViewModel(): VirtualProGlobalViewModel? = null
}