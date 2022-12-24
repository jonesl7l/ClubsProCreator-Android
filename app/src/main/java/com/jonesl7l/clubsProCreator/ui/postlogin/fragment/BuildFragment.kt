package com.jonesl7l.clubsProCreator.ui.postlogin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.jonesl7l.clubsProCreator.databinding.FragmentBuildBinding
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProHeight
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProPosition
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProPreferredFoot
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProWeight
import com.jonesl7l.clubsProCreator.ui.base.BaseFragment
import com.jonesl7l.clubsProCreator.ui.postlogin.adapter.*
import com.jonesl7l.clubsProCreator.viewmodel.VirtualProBuildSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuildFragment : BaseFragment(), ItemClickInterface {

    private lateinit var binding: FragmentBuildBinding
    private val buildViewModel: VirtualProBuildSelectionViewModel by viewModels()

    private var heightAdapter: SelectionAdapter = HeightAdapter(itemClickInterface = this)
    private var weightAdapter: SelectionAdapter = WeightAdapter(itemClickInterface = this)
    private var positionAdapter: SelectionAdapter = PositionAdapter(itemClickInterface = this)
    private var miscAdapter: MiscAdapter = MiscAdapter(itemClickInterface = this)
    private var concatAdapter: ConcatAdapter = ConcatAdapter(heightAdapter, weightAdapter, positionAdapter, miscAdapter)

    //region Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBuildBinding.inflate(LayoutInflater.from(activity), container, false)
        return binding.root
    }

    //endregion

    //region BaseFragment

    override fun initFragment() {
        binding.buildRecyclerview.adapter = concatAdapter
        buildViewModel.getCurrentVirtualProBuildStats()
    }

    override fun observeLiveData() {
        lifecycleScope.apply {
            launch {
                buildViewModel.getVirtualProHeightOptions().collect {
                    heightAdapter.updateDataSet(it)
                }
            }
            launch {
                buildViewModel.getVirtualProWeightOptions().collect {
                    weightAdapter.updateDataSet(it)
                }
            }
            launch {
                buildViewModel.getVirtualProPositionOptions().collect {
                    positionAdapter.updateDataSet(it)
                }
            }
            launch {
                buildViewModel.getVirtualProSkillPoints().collect {
                    miscAdapter.updateSkillPoints(it)
                }
            }
            launch {
                buildViewModel.getVirtualProRunningStyle().collect {
                    miscAdapter.updateRunningStyle(it)
                }
            }
            launch {
                buildViewModel.getVirtualProPreferredFootLeft().collect {
                    miscAdapter.updatePreferredFoot(it)
                }
            }
        }
    }

    //endregion

    //region ItemClickInterface

    override fun onItemClick(value: String) {
        if (value.startsWith(VirtualProHeight.HEIGHT_ID_PREFIX)) {
            buildViewModel.onHeightSelected(value.removePrefix(VirtualProHeight.HEIGHT_ID_PREFIX).toInt())
        } else if (value.startsWith(VirtualProWeight.WEIGHT_ID_PREFIX)) {
            buildViewModel.onWeightSelected(value.removePrefix(VirtualProWeight.WEIGHT_ID_PREFIX).toInt())
        } else if (value.startsWith(VirtualProPosition.POSITION_ID_PREFIX)) {
            buildViewModel.onPositionSelected(value.removePrefix(VirtualProPosition.POSITION_ID_PREFIX).toInt())
        } else if (value.startsWith(VirtualProPreferredFoot.PREFERRED_FOOT_ID_PREFIX)) {
            buildViewModel.onPreferredFootChanged(value.removePrefix(VirtualProPreferredFoot.PREFERRED_FOOT_ID_PREFIX).toInt())
        }
    }

    //endregion
}