package com.jonesl7l.clubsProCreator.ui.postlogin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.jonesl7l.clubsProCreator.databinding.FragmentTraitsBinding
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTrait
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProTraitType
import com.jonesl7l.clubsProCreator.ui.base.BaseFragment
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.TraitClickInterface
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.TraitsAdapter
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.TraitsViewPagerAdapter
import com.jonesl7l.clubsProCreator.util.notImplemented
import com.jonesl7l.clubsProCreator.viewmodel.VirtualProTraitsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TraitsFragment : BaseFragment(), TraitClickInterface {

    private lateinit var binding: FragmentTraitsBinding
    private val traitsViewModel: VirtualProTraitsViewModel by viewModels()

    private var traitsAdapter: TraitsAdapter = TraitsAdapter(itemClickInterface = this)

    //region Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTraitsBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(TraitsViewPagerAdapter.TRAITS_VIEW_PAGER_KEY) }?.apply {
            setFragment(getInt(TraitsViewPagerAdapter.TRAITS_VIEW_PAGER_KEY))
        }
    }

    //endregion

    //region BaseFragment

    override fun initFragment() {
        binding.traitsRecyclerview.adapter = traitsAdapter
        traitsViewModel.getCurrentVirtualProBuildStats()
    }

    //endregion

    //region TraitClickInterface

    override fun onTraitClick(trait: VirtualProTrait) {
        traitsViewModel.onTraitClicked(trait)
    }

    //endregion

    private fun setFragment(id: Int) {
        when (id) {
            0 -> observePhysicalLeftData()
            else -> return
        }
    }

    private fun observePhysicalLeftData() {
        lifecycleScope.apply {
            launch {
                traitsViewModel.getVirtualProPhysicalTraits().collect {
                    traitsAdapter.updateItems(it.primaryTraits, traitsViewModel.getVirtualProSelectedTraits().value, traitsViewModel.getCurrentVirtualProRemainingSkillPoints())
                }
            }
            launch {
                traitsViewModel.getVirtualProSelectedTraits().collect {
                    traitsAdapter.updateItems(traitsViewModel.getVirtualProPhysicalTraits().value.primaryTraits, it, traitsViewModel.getCurrentVirtualProRemainingSkillPoints())
                }
            }
        }
    }
}