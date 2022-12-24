package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonesl7l.clubsProCreator.ui.postlogin.fragment.TraitsFragment

class TraitsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        val fragment = TraitsFragment()
        fragment.arguments = Bundle().apply {
            putInt(TRAITS_VIEW_PAGER_KEY, position)
        }
        return fragment
    }

    override fun getItemCount(): Int = 10

    companion object {
        const val TRAITS_VIEW_PAGER_KEY = "traitsViewPagerKey"
    }
}