package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication

data class VirtualProPreferredFoot(@Ignore override var isSelected: Boolean = false, @ColumnInfo(name = "preferredFoot") var currentType: VirtualProPreferredFootType = VirtualProPreferredFootType.RIGHT): ItemInterface {

    override fun getFullIdentifier(): String = PREFERRED_FOOT_ID_PREFIX.plus(currentType.id)

    fun isEnabled(): Boolean = currentType == VirtualProPreferredFootType.RIGHT
    fun getPreferredFootValue(): String = currentType.preferredFootDisplayValue

    companion object {
        const val PREFERRED_FOOT_TEMPLATE = "{FOOT}"
        const val PREFERRED_FOOT_ID_PREFIX = "PreferredFoot:"
    }
}

enum class VirtualProPreferredFootType(val id: Int, val preferredFootDisplayValue: String) {
    LEFT(0, TemplateApplication.appContext.getString(R.string.preferred_foot_left)),
    RIGHT(1, TemplateApplication.appContext.getString(R.string.preferred_foot_right));
}