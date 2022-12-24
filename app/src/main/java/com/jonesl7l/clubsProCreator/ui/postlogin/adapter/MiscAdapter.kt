package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.databinding.ItemSelectionBinding
import com.jonesl7l.clubsProCreator.databinding.ItemSwitchBinding
import com.jonesl7l.clubsProCreator.databinding.ItemTitleBinding
import com.jonesl7l.clubsProCreator.model.MiscItemsGroup
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProPreferredFoot
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProRunningStyle
import com.jonesl7l.clubsProCreator.model.virtualpro.VirtualProSkillPoints
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SelectionViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SwitchViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.TitleViewHolder

class MiscAdapter(private val itemsGroup: MiscItemsGroup = MiscItemsGroup(), private val itemClickInterface: ItemClickInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterViewHolderType.TITLE.getTypeValue() -> TitleViewHolder(ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            AdapterViewHolderType.SWITCH.getTypeValue() -> SwitchViewHolder(ItemSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw Exception("Wrong View Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                if (position == 0) {
                    (itemsGroup.items.getOrNull(position) as? VirtualProSkillPoints)?.let {
                        val costValue = it.totalSkillPoints - it.totalTraitsCost
                        val displayString = holder.itemView.context.getString(R.string.skill_points_template)
                            .replace(VirtualProSkillPoints.SKILL_POINTS_BASE_TEMPLATE_KEY, it.totalSkillPoints.toString())
                            .replace(VirtualProSkillPoints.SKILL_POINTS_TOTAL_TEMPLATE_KEY, costValue.toString())
                        holder.bind(displayString = displayString, itemClickInterface = null)
                    }
                } else {
                    (itemsGroup.items.getOrNull(position) as? VirtualProRunningStyle)?.let {
                        val displayString = holder.itemView.context.getString(R.string.running_style_template).replace(VirtualProRunningStyle.RUNNING_STYLE_TEMPLATE, it.getRunningStyleValue())
                        holder.bind(displayString = displayString, itemClickInterface = null)
                    }
                }
            }
            is SwitchViewHolder -> {
                (itemsGroup.items.getOrNull(position) as? VirtualProPreferredFoot)?.let {
                    val displayString = holder.itemView.context.getString(R.string.preferred_foot_template).replace(VirtualProPreferredFoot.PREFERRED_FOOT_TEMPLATE, it.getPreferredFootValue())
                    holder.bind(it.getFullIdentifier(), displayString, it.isEnabled(), itemClickInterface)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0, 1 -> {
                //Skill points + Running style
                AdapterViewHolderType.TITLE.getTypeValue()
            }
            2 -> {
                //Left or Right foot
                AdapterViewHolderType.SWITCH.getTypeValue()
            }
            else -> throw Exception("Wrong View Type")
        }
    }

    fun updateSkillPoints(newSkillPoints: VirtualProSkillPoints) {
        val isFirstInit: Boolean = itemsGroup.items.getOrNull(0) == null
        if (isFirstInit) {
            itemsGroup.items.add(0, newSkillPoints)
            notifyItemRangeChanged(0, 1)
        } else {
            itemsGroup.items[0] = newSkillPoints
            notifyItemChanged(0)
        }
    }

    fun updateRunningStyle(newRunningStyle: VirtualProRunningStyle) {
        val isFirstInit: Boolean = itemsGroup.items.getOrNull(1) == null
        if (isFirstInit) {
            itemsGroup.items.add(1, newRunningStyle)
            notifyItemRangeChanged(1, 1)
        } else {
            itemsGroup.items[1] = newRunningStyle
            notifyItemChanged(1)
        }
    }

    fun updatePreferredFoot(preferLeftFoot: VirtualProPreferredFoot) {
        val isFirstInit: Boolean = itemsGroup.items.getOrNull(2) == null
        if (isFirstInit) {
            itemsGroup.items.add(2, preferLeftFoot)
            notifyItemRangeChanged(2, 1)
        } else {
            itemsGroup.items[2] = preferLeftFoot
            notifyItemChanged(2)
        }
    }

    override fun getItemCount(): Int = 3
}