package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication

data class VirtualProRunningStyle(@Ignore override var isSelected: Boolean = false, @ColumnInfo(name = "runningStyle") val currentType: VirtualProRunningStyleType = VirtualProRunningStyleType.CONTROLLED): ItemInterface {

    fun getRunningStyleValue(): String = currentType.runningStyleDisplayValue

    companion object {
        const val RUNNING_STYLE_TEMPLATE = "{STYLE}"
    }
}

enum class VirtualProRunningStyleType(val id: Int, val runningStyleDisplayValue: String) {
    LENGTHY(0, TemplateApplication.appContext.getString(R.string.running_style_lengthy)),
    CONTROLLED(1, TemplateApplication.appContext.getString(R.string.running_style_controlled)),
    EXPLOSIVE(2, TemplateApplication.appContext.getString(R.string.running_style_explosive));
}