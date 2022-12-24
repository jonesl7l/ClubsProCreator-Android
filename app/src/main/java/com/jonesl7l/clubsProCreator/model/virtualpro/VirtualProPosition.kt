package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication

data class VirtualProPosition(@Ignore override var isSelected: Boolean = false, @ColumnInfo(name = "position") var currentType: VirtualProPositionType = VirtualProPositionType.POSITION_ST): ItemInterface {

    override fun getFullIdentifier(): String = POSITION_ID_PREFIX.plus(currentType.id)

    fun getPositionValue(): String = currentType.positionDisplayValue

    companion object {
        const val POSITION_ID_PREFIX = "Position:"
        const val POSITION_TEMPLATE_KEY = "{POSITION}"
    }
}

enum class VirtualProPositionType(val id: Int, val positionDisplayValue: String) {
    POSITION_ST(0, TemplateApplication.appContext.getString(R.string.position_st)),
    POSITION_CF_LF_RF(1, TemplateApplication.appContext.getString(R.string.position_cf_lf_rf)),
    POSITION_LW_RW(2, TemplateApplication.appContext.getString(R.string.position_lw_rw)),
    POSITION_LM_RM(3, TemplateApplication.appContext.getString(R.string.position_lm_rm)),
    POSITION_CAM(4, TemplateApplication.appContext.getString(R.string.position_cam)),
    POSITION_CM(5, TemplateApplication.appContext.getString(R.string.position_cm)),
    POSITION_CDM(6, TemplateApplication.appContext.getString(R.string.position_cdm)),
    POSITION_LWB_RWB(7, TemplateApplication.appContext.getString(R.string.position_lwb_rwb)),
    POSITION_LB_RB(8, TemplateApplication.appContext.getString(R.string.position_lb_rb)),
    POSITION_CB(9, TemplateApplication.appContext.getString(R.string.position_cb)),
    POSITION_GK(10, TemplateApplication.appContext.getString(R.string.position_gk));
}