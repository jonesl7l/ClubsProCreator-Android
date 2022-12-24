package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication

@Entity
data class VirtualProHeight(@Ignore override var isSelected: Boolean = false, @ColumnInfo(name = "height") var currentType: VirtualProHeightType = VirtualProHeightType.HEIGHT_5_3): ItemInterface {

    override fun getFullIdentifier(): String = HEIGHT_ID_PREFIX.plus(currentType.id)

    fun getInchesValue(): String = currentType.inchesDisplayValue
    fun getCmValue(): String = currentType.centimetresDisplayValue

    companion object {
        const val HEIGHT_ID_PREFIX = "Height:"
        const val HEIGHT_TEMPLATE_KEY = "{HEIGHT}"
    }
}

enum class VirtualProHeightType(val id: Int, val inchesDisplayValue: String, val centimetresDisplayValue: String = "") {
    HEIGHT_5_3(0, TemplateApplication.appContext.getString(R.string.height_inches_5_3)),
    HEIGHT_5_4(1, TemplateApplication.appContext.getString(R.string.height_inches_5_4)),
    HEIGHT_5_5(2, TemplateApplication.appContext.getString(R.string.height_inches_5_5)),
    HEIGHT_5_6(3, TemplateApplication.appContext.getString(R.string.height_inches_5_6)),
    HEIGHT_5_7(4, TemplateApplication.appContext.getString(R.string.height_inches_5_7)),
    HEIGHT_5_8(5, TemplateApplication.appContext.getString(R.string.height_inches_5_8)),
    HEIGHT_5_9(6, TemplateApplication.appContext.getString(R.string.height_inches_5_9)),
    HEIGHT_5_10(7, TemplateApplication.appContext.getString(R.string.height_inches_5_10)),
    HEIGHT_5_11(8, TemplateApplication.appContext.getString(R.string.height_inches_5_11)),
    HEIGHT_6_0(9, TemplateApplication.appContext.getString(R.string.height_inches_6_0)),
    HEIGHT_6_1(10, TemplateApplication.appContext.getString(R.string.height_inches_6_1)),
    HEIGHT_6_2(11, TemplateApplication.appContext.getString(R.string.height_inches_6_2)),
    HEIGHT_6_3(12, TemplateApplication.appContext.getString(R.string.height_inches_6_3)),
    HEIGHT_6_4(13, TemplateApplication.appContext.getString(R.string.height_inches_6_4)),
    HEIGHT_6_5(14, TemplateApplication.appContext.getString(R.string.height_inches_6_5)),
    HEIGHT_6_6(15, TemplateApplication.appContext.getString(R.string.height_inches_6_6)),
    HEIGHT_6_7(16, TemplateApplication.appContext.getString(R.string.height_inches_6_7));
}
