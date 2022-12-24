package com.jonesl7l.clubsProCreator.ui.postlogin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.jonesl7l.clubsProCreator.databinding.FragmentStatsBinding
import com.jonesl7l.clubsProCreator.model.*
import com.jonesl7l.clubsProCreator.ui.base.BaseFragment
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.StatsAdapter
import com.jonesl7l.clubsProCreator.viewmodel.VirtualProStatsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatsFragment : BaseFragment() {

    private lateinit var binding: FragmentStatsBinding
    private val statsViewModel: VirtualProStatsViewModel by viewModels()

    private var physicalAdapter: StatsAdapter = StatsAdapter(PhysicalItemsGroup())
    private var defendingAdapter: StatsAdapter = StatsAdapter(DefendingItemsGroup())
    private var dribblingAdapter: StatsAdapter = StatsAdapter(DribblingItemsGroup())
    private var passingAdapter: StatsAdapter = StatsAdapter(PassingItemsGroup())
    private var shootingAdapter: StatsAdapter = StatsAdapter(ShootingItemsGroup())
    private var paceAdapter: StatsAdapter = StatsAdapter(PaceItemsGroup())

    //    private var goalkeepingAdapter: StatsAdapter = StatsAdapter()
    private var concatAdapter: ConcatAdapter = ConcatAdapter(
        physicalAdapter, defendingAdapter, dribblingAdapter, passingAdapter,
        shootingAdapter, paceAdapter)//, goalkeepingAdapter)

    //region Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStatsBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    //endregion

    //region BaseFragment

    override fun initFragment() {
        binding.statsRecyclerview.adapter = concatAdapter
        statsViewModel.getCurrentVirtualProBuildStats()
    }

    override fun observeLiveData() {
        lifecycleScope.launch {
            launch {
                statsViewModel.getVirtualProPhysicalStats().collect {
                    physicalAdapter.updateDataSet(it)
                }
            }
            launch {
                statsViewModel.getVirtualProDefendingStats().collect {
                    defendingAdapter.updateDataSet(it)
                }
            }
            launch {
                statsViewModel.getVirtualProDribblingStats().collect {
                    dribblingAdapter.updateDataSet(it)
                }
            }
            launch {
                statsViewModel.getVirtualProPassingStats().collect {
                    passingAdapter.updateDataSet(it)
                }
            }
            launch {
                statsViewModel.getVirtualProShootingStats().collect {
                    shootingAdapter.updateDataSet(it)
                }
            }
            launch {
                statsViewModel.getVirtualProPaceStats().collect {
                    paceAdapter.updateDataSet(it)
                }
            }
        }
    }

    //endregion
}