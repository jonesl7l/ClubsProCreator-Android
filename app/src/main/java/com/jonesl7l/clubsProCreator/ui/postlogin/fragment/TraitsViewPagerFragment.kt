package com.jonesl7l.clubsProCreator.ui.postlogin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.databinding.FragmentTraitsViewpagerBinding
import com.jonesl7l.clubsProCreator.ui.base.BaseFragment
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.TraitsViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TraitsViewPagerFragment : BaseFragment() {

    private lateinit var binding: FragmentTraitsViewpagerBinding

    private lateinit var traitsAdapter: TraitsViewPagerAdapter

    //region Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTraitsViewpagerBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    //endregion

    //region BaseFragment

    override fun initFragment() {
        traitsAdapter = TraitsViewPagerAdapter(this)
        binding.traitsViewPager.adapter = traitsAdapter
        binding.traitsViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val stringId = when (position) {
                    0 -> R.string.physical_left_title
                    1 -> R.string.physical_right_title
                    else -> return
                }
                binding.traitsTitle.text = getString(stringId)
            }
        })
        TabLayoutMediator(binding.traitsTabLayout, binding.traitsViewPager) { _, _ ->}.attach()
    }

    //endregion
}