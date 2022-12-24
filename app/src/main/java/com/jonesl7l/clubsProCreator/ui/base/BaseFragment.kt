package com.jonesl7l.clubsProCreator.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment: Fragment() {

    protected var activityCallback: GenericActivityCallback? = null

    //region Fragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityCallback = context as GenericActivityCallback
        } catch (castException: ClassCastException) {
            Timber.e(castException)
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
        observeLiveData()
    }

    override fun onDetach() {
        super.onDetach()
        activityCallback = null
    }

    //endregion

    //region Overrides

    open fun initFragment() {
        //Override when necessary
    }

    open fun observeLiveData() {
        //Override when necessary
    }

    //endregion
}
