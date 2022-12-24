package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication

data class VirtualProWeight(override var isSelected: Boolean = false, @ColumnInfo(name = "weight") var currentType: VirtualProWeightType = VirtualProWeightType.WEIGHT_99_TO_119): ItemInterface {

    override fun getFullIdentifier(): String = WEIGHT_ID_PREFIX.plus(currentType.id)

    fun getPoundsValue(): String = currentType.poundsDisplayValue
    fun getGramsValue(): String = currentType.gramsDisplayValue

    companion object {
        const val WEIGHT_ID_PREFIX = "Weight:"
        const val WEIGHT_TEMPLATE_KEY = "{WEIGHT}"

    }
}

enum class VirtualProWeightType(val id: Int, val poundsDisplayValue: String, val gramsDisplayValue: String = "") {
    WEIGHT_99_TO_119(0, TemplateApplication.appContext.getString(R.string.weight_pounds_99_to_119)),
    WEIGHT_121_TO_149(1, TemplateApplication.appContext.getString(R.string.weight_pounds_121_to_149)),
    WEIGHT_152_TO_174(2, TemplateApplication.appContext.getString(R.string.weight_pounds_152_to_174)),
    WEIGHT_176_TO_198(3, TemplateApplication.appContext.getString(R.string.weight_pounds_176_to_198)),
    WEIGHT_200_TO_224(4, TemplateApplication.appContext.getString(R.string.weight_pounds_200_to_224)),
    WEIGHT_227_TO_253(5, TemplateApplication.appContext.getString(R.string.weight_pounds_227_to_253));
}