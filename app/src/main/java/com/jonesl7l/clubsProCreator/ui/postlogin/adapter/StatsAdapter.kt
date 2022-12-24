package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext
import com.jonesl7l.clubsProCreator.databinding.ItemPrimaryStatBarBinding
import com.jonesl7l.clubsProCreator.databinding.ItemSecondaryStatBarBinding
import com.jonesl7l.clubsProCreator.databinding.ItemStarStatBarBinding
import com.jonesl7l.clubsProCreator.model.StatsItemGroups
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.PrimaryStatViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.SecondaryStatViewHolder
import com.jonesl7l.clubsProCreator.ui.postlogin.viewholder.StarStatViewHolder
import com.jonesl7l.clubsProCreator.util.orZero
import com.jonesl7l.clubsProCreator.util.then
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class StatsAdapter(private var itemsGroup: StatsItemGroups? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isExpanded: Boolean by Delegates.observable(false) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, itemsGroup?.items?.size.orZero())
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, itemsGroup?.items?.size.orZero())
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterViewHolderType.STAT_PRIMARY.getTypeValue() -> PrimaryStatViewHolder(ItemPrimaryStatBarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            AdapterViewHolderType.STAT_SECONDARY.getTypeValue() -> SecondaryStatViewHolder(ItemSecondaryStatBarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            AdapterViewHolderType.STAT_RATING.getTypeValue() -> StarStatViewHolder(ItemStarStatBarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> {
                throw Exception("Wrong View Type")
            }
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (itemsGroup == null) return
        if (position == 0) {
            (holder as? PrimaryStatViewHolder)?.bind(itemsGroup?.title.orEmpty(), itemsGroup?.baseStat.orZero(), itemsGroup?.stat.orZero(), itemClickInterface = onHeaderClickListener)
        } else {
            val item = itemsGroup?.items?.getOrNull(position.minus(1))
            (holder as? SecondaryStatViewHolder)?.bind(item?.title.orEmpty(), item?.baseStat.orZero(), item?.stat.orZero())
            (holder as? StarStatViewHolder)?.bind(item?.title.orEmpty(), item?.baseStat.orZero(), item?.stat.orZero())
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            //Will default to the method above
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads[0] as Bundle
            for (key in payload.keySet()) {
                if (holder is PrimaryStatViewHolder) {
                    when (key) {
                        ON_BASE_STAT_UPDATE_KEY -> {
                            if (position == 0) {
                                holder.updateBaseStat(itemsGroup?.baseStat.orZero())
                            } else {
                                holder.updateBaseStat(itemsGroup?.items?.getOrNull(position.minus(1))?.baseStat.orZero())
                            }
                        }
                        ON_BOOSTED_STAT_UPDATE_KEY -> {
                            if (position == 0) {
                                holder.updateBoostedStat(itemsGroup?.stat.orZero())
                            } else {
                                holder.updateBoostedStat(itemsGroup?.items?.getOrNull(position.minus(1))?.stat.orZero())
                            }
                        }
                    }
                } else if (holder is SecondaryStatViewHolder) {
                    when (key) {
                        ON_BASE_STAT_UPDATE_KEY -> {
                            if (position == 0) {
                                holder.updateBaseStat(itemsGroup?.baseStat.orZero())
                            } else {
                                holder.updateBaseStat(itemsGroup?.items?.getOrNull(position.minus(1))?.baseStat.orZero())
                            }
                        }
                        ON_BOOSTED_STAT_UPDATE_KEY -> {
                            if (position == 0) {
                                holder.updateBoostedStat(itemsGroup?.stat.orZero())
                            } else {
                                holder.updateBoostedStat(itemsGroup?.items?.getOrNull(position.minus(1))?.stat.orZero())
                            }
                        }
                    }
                } else if (holder is StarStatViewHolder) {
                    when (key) {
                        ON_BOOSTED_STAT_UPDATE_KEY -> {
                            holder.updateRating(itemsGroup?.items?.getOrNull(position.minus(1))?.stat.orZero())
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = if (isExpanded) itemsGroup?.items?.size.orZero().plus(1) else 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            AdapterViewHolderType.STAT_PRIMARY.getTypeValue()
        } else {
            val truePosition = position.minus(1)
            val currentItem = itemsGroup?.items?.getOrNull(truePosition)
            if (currentItem?.title == appContext.getString(R.string.shooting_weak_foot) ||
                currentItem?.title == appContext.getString(R.string.dribbling_skill_moves)
            ) {
                AdapterViewHolderType.STAT_RATING.getTypeValue()
            } else {
                AdapterViewHolderType.STAT_SECONDARY.getTypeValue()
            }
        }
    }

    fun updateDataSet(statsItemsGroup: StatsItemGroups) {

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldNewPosition = if (oldItemPosition == 0) oldItemPosition else oldItemPosition.minus(1)
                val trueNewPosition = if (newItemPosition == 0) newItemPosition else newItemPosition.minus(1)

                return if (oldItemPosition == 0 && newItemPosition == 0) {
                    statsItemsGroup.title == itemsGroup?.title
                } else {
                    val newItem = statsItemsGroup.items.getOrNull(trueNewPosition)
                    val oldItem = itemsGroup?.items?.getOrNull(oldNewPosition)
                    newItem?.title == oldItem?.title
                }
            }

            override fun getOldListSize(): Int = if (isExpanded) itemsGroup?.items?.size?.plus(1).orZero() else 1

            override fun getNewListSize(): Int = if (isExpanded) statsItemsGroup.items.size.plus(1) else 1

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldNewPosition = if (oldItemPosition == 0) oldItemPosition else oldItemPosition.minus(1)
                val trueNewPosition = if (newItemPosition == 0) newItemPosition else newItemPosition.minus(1)
                return if (oldItemPosition == 0 && newItemPosition == 0) {
                    statsItemsGroup.title == itemsGroup?.title &&
                            statsItemsGroup.stat == itemsGroup?.stat &&
                            statsItemsGroup.baseStat == itemsGroup?.baseStat
                } else {
                    val newItem = statsItemsGroup.items.getOrNull(trueNewPosition)
                    val oldItem = itemsGroup?.items?.getOrNull(oldNewPosition)
                    newItem?.title == oldItem?.title && newItem?.baseStat == oldItem?.baseStat && newItem?.stat == oldItem?.stat
                }
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
                val trueNewPosition = if (newItemPosition == 0) newItemPosition else newItemPosition.minus(1)
                val trueOldPosition = if (oldItemPosition == 0) oldItemPosition else oldItemPosition.minus(1)

                val newValue: StatsItemGroups? = if (trueNewPosition == 0) statsItemsGroup else statsItemsGroup.items.getOrNull(trueNewPosition)
                val oldValue: StatsItemGroups? = if (trueOldPosition == 0) itemsGroup else itemsGroup?.items?.getOrNull(trueOldPosition)
                val diff = Bundle()
                if (newValue?.stat != oldValue?.stat) {
                    diff.putInt(ON_BOOSTED_STAT_UPDATE_KEY, newValue?.stat.orZero())
                }
                if (newValue?.baseStat != oldValue?.baseStat) {
                    diff.putInt(ON_BASE_STAT_UPDATE_KEY, newValue?.baseStat.orZero())
                }
                return diff.isEmpty then null ?: diff
            }
        })

        diffResult.dispatchUpdatesTo(this)
        itemsGroup?.title = statsItemsGroup.title
        itemsGroup?.stat = statsItemsGroup.stat
        itemsGroup?.baseStat = statsItemsGroup.baseStat
        itemsGroup?.items?.clear()
        itemsGroup?.items?.addAll(statsItemsGroup.items)
    }

    companion object {

        private const val ON_BOOSTED_STAT_UPDATE_KEY = "boostedStat"
        private const val ON_BASE_STAT_UPDATE_KEY = "baseStat"
    }
}