package com.jonesl7l.clubsProCreator.di

import com.google.firebase.storage.FirebaseStorage
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext
import com.jonesl7l.clubsProCreator.model.*
import com.jonesl7l.clubsProCreator.model.virtualpro.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region Firebase

    @Provides
    @Singleton
    fun getFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    //endregion

    //region Build

    @Provides
    @Singleton
    fun getHeightItemsGroup(): HeightItemsGroup = HeightItemsGroup(
        items = mutableListOf(
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_3),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_4),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_5),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_6),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_7),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_8),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_9),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_10),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_5_11),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_0),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_1),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_2),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_3),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_4),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_5),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_6),
            VirtualProHeight(currentType = VirtualProHeightType.HEIGHT_6_7)
        )
    )

    @Provides
    @Singleton
    fun getWeightItemsGroup(): WeightItemsGroup = WeightItemsGroup(
        items = mutableListOf(
            VirtualProWeight(currentType = VirtualProWeightType.WEIGHT_99_TO_119),
            VirtualProWeight(currentType = VirtualProWeightType.WEIGHT_121_TO_149),
            VirtualProWeight(currentType = VirtualProWeightType.WEIGHT_152_TO_174),
            VirtualProWeight(currentType = VirtualProWeightType.WEIGHT_176_TO_198),
            VirtualProWeight(currentType = VirtualProWeightType.WEIGHT_200_TO_224),
            VirtualProWeight(currentType = VirtualProWeightType.WEIGHT_227_TO_253),
        )
    )

    @Provides
    @Singleton
    fun getPositionItemsGroup(): PositionItemsGroup = PositionItemsGroup(
        items = mutableListOf(
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_ST),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_CF_LF_RF),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_LW_RW),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_LM_RM),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_CAM),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_CM),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_CDM),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_LWB_RWB),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_LB_RB),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_CB),
            VirtualProPosition(currentType = VirtualProPositionType.POSITION_GK),
        )
    )

    //endregion

    //region Stats

    @Provides
    @Singleton
    fun getPhysicalItemsGroup(): PhysicalItemsGroup = PhysicalItemsGroup(
        appContext.getString(R.string.physical_title),
        items = mutableListOf(
            PhysicalItemsGroup(appContext.getString(R.string.physical_jumping)),
            PhysicalItemsGroup(appContext.getString(R.string.physical_stamina)),
            PhysicalItemsGroup(appContext.getString(R.string.physical_strength)),
            PhysicalItemsGroup(appContext.getString(R.string.physical_reactions)),
            PhysicalItemsGroup(appContext.getString(R.string.physical_aggression))
        )
    )

    @Provides
    @Singleton
    fun getDefendingItemsGroup(): DefendingItemsGroup = DefendingItemsGroup(
        appContext.getString(R.string.defending_title),
        items = mutableListOf(
            DefendingItemsGroup(appContext.getString(R.string.defending_def_awareness)),
            DefendingItemsGroup(appContext.getString(R.string.defending_interceptions)),
            DefendingItemsGroup(appContext.getString(R.string.defending_standing_tackle)),
            DefendingItemsGroup(appContext.getString(R.string.defending_slide_tackle))
        )
    )


    @Provides
    @Singleton
    fun getDribblingItemsGroup(): DribblingItemsGroup = DribblingItemsGroup(
        appContext.getString(R.string.dribbling_title),
        items = mutableListOf(
            DribblingItemsGroup(appContext.getString(R.string.dribbling_agility)),
            DribblingItemsGroup(appContext.getString(R.string.dribbling_balance)),
            DribblingItemsGroup(appContext.getString(R.string.dribbling_att_position)),
            DribblingItemsGroup(appContext.getString(R.string.dribbling_ball_control)),
            DribblingItemsGroup(appContext.getString(R.string.dribbling_dribbling)),
            DribblingItemsGroup(appContext.getString(R.string.dribbling_skill_moves))
        )
    )

    @Provides
    @Singleton
    fun getPassingItemsGroup(): PassingItemsGroup = PassingItemsGroup(
        appContext.getString(R.string.passing_title),
        items = mutableListOf(
            PassingItemsGroup(appContext.getString(R.string.passing_vision)),
            PassingItemsGroup(appContext.getString(R.string.passing_crossing)),
            PassingItemsGroup(appContext.getString(R.string.passing_short)),
            PassingItemsGroup(appContext.getString(R.string.passing_long)),
            PassingItemsGroup(appContext.getString(R.string.passing_curve))
        )
    )

    @Provides
    @Singleton
    fun getShootingItemsGroup(): ShootingItemsGroup = ShootingItemsGroup(
        appContext.getString(R.string.shooting_title),
        items = mutableListOf(
            ShootingItemsGroup(appContext.getString(R.string.shooting_finishing)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_fk_acc)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_heading_acc)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_shot_power)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_long_shot)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_volleys)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_penalties)),
            ShootingItemsGroup(appContext.getString(R.string.shooting_weak_foot))
        )
    )

    @Provides
    @Singleton
    fun getPaceItemsGroup(): PaceItemsGroup = PaceItemsGroup(
        appContext.getString(R.string.pace_title),
        items = mutableListOf(
            PassingItemsGroup(appContext.getString(R.string.pace_acceleration)),
            PassingItemsGroup(appContext.getString(R.string.pace_sprint_speed))
        )
    )

    //endregion

    //region Traits

    @Provides
    @Singleton
    fun getPhysicalTraits(): PhysicalTraitsItemsGroup = PhysicalTraitsItemsGroup(
        mutableListOf(
            //First row
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            VirtualProTrait(PhysicalLeftTrait.STR_ONE.id, appContext.getString(R.string.physical_trait_str_one), 1, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(strength = 1)),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            //Second row
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(topVisible = true, bottomVisible = true),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            //Third row
            VirtualProTrait(PhysicalLeftTrait.REACTIONS_ONE.id, appContext.getString(R.string.physical_trait_reactions_three), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(reactions = 3), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id, (PhysicalLeftTrait.STR_TWO.id))),
            VirtualProTraitDivider(startVisible = true, endVisible = true),
            VirtualProTrait(PhysicalLeftTrait.STR_TWO.id, appContext.getString(R.string.physical_trait_str_two), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(strength = 2), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id)),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            //Fourth row
            VirtualProTraitDivider(endVisible = true, bottomVisible = true),
            VirtualProTraitDivider(startVisible = true, endVisible = true),
            VirtualProTraitDivider(topVisible = true, bottomVisible = true, startVisible = true, endVisible = true),
            VirtualProTraitDivider(startVisible = true, endVisible = true),
            VirtualProTraitDivider(startVisible = true, bottomVisible = true),
            //fifth row
            VirtualProTrait(PhysicalLeftTrait.STR_THREE.id, appContext.getString(R.string.physical_trait_str_two), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(strength = 2), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id, PhysicalLeftTrait.STR_TWO.id)),
            VirtualProTraitDivider(),
            VirtualProTrait(PhysicalLeftTrait.AGGRESSION_ONE.id, appContext.getString(R.string.physical_trait_aggression_two), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(aggression = 2), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id, PhysicalLeftTrait.STR_TWO.id)),
            VirtualProTraitDivider(),
            VirtualProTrait(PhysicalLeftTrait.AGGRESSION_TWO.id, appContext.getString(R.string.physical_trait_aggression_three), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(aggression = 3), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id ,PhysicalLeftTrait.STR_TWO.id)),
            //Sixth row
            VirtualProTraitDivider(topVisible = true, bottomVisible = true, endVisible = true),
            VirtualProTraitDivider(startVisible = true, endVisible = true),
            VirtualProTraitDivider(startVisible = true, bottomVisible = true),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            //Seventh row
            VirtualProTrait(PhysicalLeftTrait.AGGRESSION_THREE.id, appContext.getString(R.string.physical_trait_aggression_three), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(aggression = 3), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id ,PhysicalLeftTrait.STR_TWO.id, PhysicalLeftTrait.STR_THREE.id)),
            VirtualProTraitDivider(),
            VirtualProTrait(PhysicalLeftTrait.BULL.id, appContext.getString(R.string.physical_trait_bull), 10, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(aggression = 6, strength = 6), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id ,PhysicalLeftTrait.STR_TWO.id, PhysicalLeftTrait.STR_THREE.id)),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            //Eighth row
            VirtualProTraitDivider(topVisible = true, bottomVisible = true),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            //Ninth row
            VirtualProTrait(PhysicalLeftTrait.REACTIONS_TWO.id, appContext.getString(R.string.physical_trait_reactions_three), 2, traitType = VirtualProTraitType.PHYSICAL_LEFT, physical = PhysicalStats(reactions = 3), prerequisite = listOf(PhysicalLeftTrait.STR_ONE.id ,PhysicalLeftTrait.STR_TWO.id, PhysicalLeftTrait.STR_THREE.id, PhysicalLeftTrait.AGGRESSION_THREE.id)),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            VirtualProTraitDivider(),
            VirtualProTraitDivider()
        )
    )


    //endregion
}