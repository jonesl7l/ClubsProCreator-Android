package com.jonesl7l.clubsProCreator.ui.postlogin.adapter

enum class AdapterViewHolderType(private val viewHolderType: Int) {
    TITLE(0),
    SELECTION(1),
    STATS(2),
    SWITCH(3),
    STAT_PRIMARY(4),
    STAT_SECONDARY(5),
    STAT_RATING(6),
    TRAIT(7),
    LINK(8);


    fun getTypeValue(): Int = this.viewHolderType
}