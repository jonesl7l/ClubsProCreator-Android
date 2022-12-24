package com.jonesl7l.clubsProCreator.model

import com.jonesl7l.clubsProCreator.model.virtualpro.ItemInterface

interface SelectionItemGroups {
    var title: String
    val items: MutableList<ItemInterface>
}

interface StatsItemGroups {
    var title: String
    var baseStat: Int
    var stat: Int
    var items: MutableList<StatsItemGroups>
}

data class HeightItemsGroup(override var title: String = "", override val items: MutableList<ItemInterface> = mutableListOf()) : SelectionItemGroups

data class WeightItemsGroup(override var title: String = "", override val items: MutableList<ItemInterface> = mutableListOf()) : SelectionItemGroups

data class PositionItemsGroup(override var title: String = "", override val items: MutableList<ItemInterface> = mutableListOf()) : SelectionItemGroups

data class MiscItemsGroup(override var title: String = "", override val items: MutableList<ItemInterface> = mutableListOf()) : SelectionItemGroups

data class PhysicalItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups

data class DefendingItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups

data class DribblingItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups

data class PassingItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups

data class ShootingItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups

data class PaceItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups

data class GoalkeepingItemsGroup(override var title: String = "", override var baseStat: Int = 0, override var stat: Int = 0, override var items: MutableList<StatsItemGroups> = mutableListOf()) :
    StatsItemGroups