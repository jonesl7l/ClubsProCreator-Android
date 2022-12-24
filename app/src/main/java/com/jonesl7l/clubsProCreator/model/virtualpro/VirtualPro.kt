package com.jonesl7l.clubsProCreator.model.virtualpro

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.TemplateApplication.Companion.appContext

@Entity
data class VirtualPro(
    @PrimaryKey var name: String = "PLAY STYLE 1",
    @Embedded var height: VirtualProHeight = VirtualProHeight(),
    @Embedded var weight: VirtualProWeight = VirtualProWeight(),
    @Embedded var position: VirtualProPosition = VirtualProPosition(),
    @ColumnInfo(name = "totalSkillPoints") var totalSkillPoints: Int = 185,

    @Embedded var basePhysical: PhysicalStats = PhysicalStats(),
    @Embedded var baseDefending: DefendingStats = DefendingStats(),
    @Embedded var baseDribbling: DribblingStats = DribblingStats(),
    @Embedded var basePassing: PassingStats = PassingStats(),
    @Embedded var baseShooting: ShootingStats = ShootingStats(),
    @Embedded var basePace: PaceStats = PaceStats(),
    @Embedded var baseGoalkeeping: GoalkeepingStats = GoalkeepingStats(),

    @Embedded var preferredFoot: VirtualProPreferredFoot = VirtualProPreferredFoot(),
    @ColumnInfo(name = "selectedTraits") var selectedTraits: MutableList<VirtualProTrait> = mutableListOf()
) {

    constructor(currentPro: VirtualPro) : this() {
        name = currentPro.name
        height = VirtualProHeight(currentPro.height.isSelected, currentPro.height.currentType)
        weight = VirtualProWeight(currentPro.weight.isSelected, currentPro.weight.currentType)
        position = VirtualProPosition(currentPro.position.isSelected, currentPro.position.currentType)
        totalSkillPoints = currentPro.totalSkillPoints

        basePhysical = PhysicalStats(currentPro.basePhysical)
        baseDefending = DefendingStats(currentPro.baseDefending)
        baseDribbling = DribblingStats(currentPro.baseDribbling)
        basePassing = PassingStats(currentPro.basePassing)
        baseShooting = ShootingStats(currentPro.baseShooting)
        basePace = PaceStats(currentPro.basePace)
        baseGoalkeeping = GoalkeepingStats(currentPro.baseGoalkeeping)

        preferredFoot = currentPro.preferredFoot
        selectedTraits.addAll(currentPro.selectedTraits)
    }

    fun physical(): PhysicalStats {
        val physicalTraitsList = mutableListOf<PhysicalStats>()
        selectedTraits.forEach { it.physical?.let { it1 -> physicalTraitsList.add(it1) } }

        return basePhysical.combine(physicalTraitsList)
    }

    fun defending(): DefendingStats {
        val defendingTraitsList = mutableListOf<DefendingStats>()
        selectedTraits.forEach { it.defending?.let { it1 -> defendingTraitsList.add(it1) } }

        return baseDefending.combine(defendingTraitsList)
    }

    fun dribbling(): DribblingStats {
        val dribblingTraitsList = mutableListOf<DribblingStats>()
        selectedTraits.forEach { it.dribbling?.let { it1 -> dribblingTraitsList.add(it1) } }

        return baseDribbling.combine(dribblingTraitsList)
    }

    fun passing(): PassingStats {
        val passingTraitsList = mutableListOf<PassingStats>()
        selectedTraits.forEach { it.passing?.let { it1 -> passingTraitsList.add(it1) } }

        return basePassing.combine(passingTraitsList)
    }

    fun shooting(): ShootingStats {
        val shootingTraitsList = mutableListOf<ShootingStats>()
        selectedTraits.forEach { it.shooting?.let { it1 -> shootingTraitsList.add(it1) } }

        return baseShooting.combine(shootingTraitsList)
    }

    fun pace(): PaceStats {
        val paceTraitsList = mutableListOf<PaceStats>()
        selectedTraits.forEach { it.pace?.let { it1 -> paceTraitsList.add(it1) } }

        return basePace.combine(paceTraitsList)
    }

    fun goalkeeping(): GoalkeepingStats {
        val goalkeepingTraitsList = mutableListOf<GoalkeepingStats>()
        selectedTraits.forEach { it.goalkeeping?.let { it1 -> goalkeepingTraitsList.add(it1) } }

        return baseGoalkeeping.combine(goalkeepingTraitsList)
    }

    fun totalTraitsCost(): Int {
        var cost = 0
        selectedTraits.forEach { cost += it.cost }
        return cost
    }


    fun runningStyle(): VirtualProRunningStyleType {
        val explosiveHeights: Array<VirtualProHeightType> = arrayOf(
            VirtualProHeightType.HEIGHT_5_3,
            VirtualProHeightType.HEIGHT_5_4,
            VirtualProHeightType.HEIGHT_5_5,
            VirtualProHeightType.HEIGHT_5_6,
            VirtualProHeightType.HEIGHT_5_7,
            VirtualProHeightType.HEIGHT_5_8,
            VirtualProHeightType.HEIGHT_5_9,
            VirtualProHeightType.HEIGHT_5_10,
            VirtualProHeightType.HEIGHT_5_11
        )

        val lengthyHeights: Array<VirtualProHeightType> = arrayOf(
            VirtualProHeightType.HEIGHT_5_9,
            VirtualProHeightType.HEIGHT_5_10,
            VirtualProHeightType.HEIGHT_5_11,
            VirtualProHeightType.HEIGHT_6_0,
            VirtualProHeightType.HEIGHT_6_1,
            VirtualProHeightType.HEIGHT_6_2,
            VirtualProHeightType.HEIGHT_6_3,
            VirtualProHeightType.HEIGHT_6_4,
            VirtualProHeightType.HEIGHT_6_5,
            VirtualProHeightType.HEIGHT_6_6,
            VirtualProHeightType.HEIGHT_6_7
        )

        return if (explosiveHeights.contains(height.currentType) &&
            dribbling().agility >= 65 &&
            (dribbling().agility - physical().strength) >= 15 &&
            pace().acceleration >= 74
        ) {
            VirtualProRunningStyleType.EXPLOSIVE
        } else if (lengthyHeights.contains(height.currentType) &&
            physical().strength > 65 &&
            (physical().strength - dribbling().agility) >= 14 &&
            pace().acceleration > 55
        ) {
            VirtualProRunningStyleType.LENGTHY
        } else {
            VirtualProRunningStyleType.CONTROLLED
        }
    }

    fun overallBasePhysical(): Int {
        return (basePhysical.aggression + basePhysical.jumping + basePhysical.strength + basePhysical.stamina + basePhysical.reactions) / 5
    }

    fun overallBaseDefending(): Int {
        return (baseDefending.interceptions + baseDefending.defAwareness + baseDefending.standingTackle + baseDefending.slideTackle) / 4
    }

    fun overallBaseDribbling(): Int {
        return (baseDribbling.agility + baseDribbling.dribbling + baseDribbling.balance + baseDribbling.attPosition + baseDribbling.ballControl) / 5
    }

    fun overallBasePassing(): Int {
        return (basePassing.longPass + basePassing.shortPass + basePassing.vision + basePassing.crossing + basePassing.curve) / 5
    }

    fun overallBaseShooting(): Int {
        return (baseShooting.finishing + baseShooting.fkAccuracy + baseShooting.shotPower + baseShooting.longShot + baseShooting.headingAccuracy + baseShooting.volleys + baseShooting.penalties) / 7
    }

    fun overallBasePace(): Int {
        return (basePace.acceleration + basePace.sprintSpeed) / 2
    }

    fun overallBaseGoalkeeping(): Int {
        return (baseGoalkeeping.gkDiving + baseGoalkeeping.gkHandling + baseGoalkeeping.gkKicking + baseGoalkeeping.gkReflexes + baseGoalkeeping.gkPositioning) / 5
    }

    fun overallPhysical(): Int {
        return (physical().aggression + physical().jumping + physical().strength + physical().stamina + physical().reactions) / 5
    }

    fun overallDefending(): Int {
        return (defending().interceptions + defending().defAwareness + defending().standingTackle + defending().slideTackle) / 4
    }

    fun overallDribbling(): Int {
        return (dribbling().agility + dribbling().dribbling + dribbling().balance + dribbling().attPosition + dribbling().ballControl) / 5
    }

    fun overallPassing(): Int {
        return (passing().longPass + passing().shortPass + passing().vision + passing().crossing + passing().curve) / 5
    }

    fun overallShooting(): Int {
        return (shooting().finishing + shooting().fkAccuracy + shooting().shotPower + shooting().longShot + shooting().headingAccuracy + shooting().volleys + shooting().penalties) / 7
    }

    fun overallPace(): Int {
        return (pace().acceleration + pace().sprintSpeed) / 2
    }

    fun overallGoalkeeping(): Int {
        return (goalkeeping().gkDiving + goalkeeping().gkHandling + goalkeeping().gkKicking + goalkeeping().gkReflexes + goalkeeping().gkPositioning) / 5
    }

    companion object {
        const val BASE_STAT_TEMPLATE_KEY = "{BASE_STAT}"
        const val BOOSTED_STAT_TEMPLATE_KEY = "{BOOST}"
    }
}

@Entity
data class PhysicalStats(
    @ColumnInfo(name = "jumping") var jumping: Int = 0,
    @ColumnInfo(name = "stamina") var stamina: Int = 0,
    @ColumnInfo(name = "strength") var strength: Int = 0,
    @ColumnInfo(name = "reactions") var reactions: Int = 0,
    @ColumnInfo(name = "aggression") var aggression: Int = 0
) {

    constructor(currentProStats: PhysicalStats) : this() {
        jumping = currentProStats.jumping
        stamina = currentProStats.stamina
        strength = currentProStats.strength
        reactions = currentProStats.reactions
        aggression = currentProStats.aggression
    }

    fun combine(traitStats: List<PhysicalStats>): PhysicalStats {
        val combinedStats = PhysicalStats()
        combinedStats.jumping = jumping
        combinedStats.stamina = stamina
        combinedStats.strength = strength
        combinedStats.reactions = reactions
        combinedStats.aggression = aggression

        traitStats.forEach {
            combinedStats.jumping += it.jumping
            combinedStats.stamina += it.stamina
            combinedStats.strength += it.strength
            combinedStats.reactions += it.reactions
            combinedStats.aggression += it.aggression
        }
        return combinedStats
    }

    companion object {
        private const val JUMPING_KEY = "jumping"
        private const val STAMINA_KEY = "stamina"
        private const val STRENGTH_KEY = "strength"
        private const val REACTIONS_KEY = "reactions"
        private const val AGGRESSION_KEY = "aggression"
    }
}

@Entity
data class DefendingStats(
    @ColumnInfo(name = "interceptions") var interceptions: Int = 0,
    @ColumnInfo(name = "defAwareness") var defAwareness: Int = 0,
    @ColumnInfo(name = "standingTackle") var standingTackle: Int = 0,
    @ColumnInfo(name = "slideTackle") var slideTackle: Int = 0
) {

    constructor(currentProStats: DefendingStats) : this() {
        interceptions = currentProStats.interceptions
        defAwareness = currentProStats.defAwareness
        standingTackle = currentProStats.standingTackle
        slideTackle = currentProStats.slideTackle
    }

    fun combine(traitStats: List<DefendingStats>): DefendingStats {
        val combinedStats = DefendingStats()
        combinedStats.interceptions = interceptions
        combinedStats.defAwareness = defAwareness
        combinedStats.standingTackle = standingTackle
        combinedStats.slideTackle = slideTackle

        traitStats.forEach {
            combinedStats.interceptions += it.interceptions
            combinedStats.defAwareness += it.defAwareness
            combinedStats.standingTackle += it.standingTackle
            combinedStats.slideTackle += it.slideTackle
        }
        return combinedStats
    }
}

@Entity
data class DribblingStats(
    @ColumnInfo(name = "agility") var agility: Int = 0,
    @ColumnInfo(name = "balance") var balance: Int = 0,
    @ColumnInfo(name = "attPosition") var attPosition: Int = 0,
    @ColumnInfo(name = "ballControl") var ballControl: Int = 0,
    @ColumnInfo(name = "dribbling") var dribbling: Int = 0,
    @ColumnInfo(name = "skillMoves") var skillMoves: Int = 3
) {

    constructor(currentProStats: DribblingStats) : this() {
        agility = currentProStats.agility
        balance = currentProStats.balance
        attPosition = currentProStats.attPosition
        ballControl = currentProStats.ballControl
        dribbling = currentProStats.dribbling
        skillMoves = currentProStats.skillMoves
    }

    fun combine(traitStats: List<DribblingStats>): DribblingStats {
        val combinedStats = DribblingStats()
        combinedStats.agility = agility
        combinedStats.balance = balance
        combinedStats.attPosition = attPosition
        combinedStats.ballControl = ballControl
        combinedStats.dribbling = dribbling
        combinedStats.skillMoves = skillMoves

        traitStats.forEach {
            combinedStats.agility += it.agility
            combinedStats.balance += it.balance
            combinedStats.attPosition += it.attPosition
            combinedStats.ballControl += it.ballControl
            combinedStats.dribbling += it.dribbling
            combinedStats.skillMoves += it.skillMoves
        }
        return combinedStats
    }
}

@Entity
data class PassingStats(
    @ColumnInfo(name = "vision") var vision: Int = 0,
    @ColumnInfo(name = "crossing") var crossing: Int = 0,
    @ColumnInfo(name = "longPass") var longPass: Int = 0,
    @ColumnInfo(name = "shortPass") var shortPass: Int = 0,
    @ColumnInfo(name = "curve") var curve: Int = 0
) {

    constructor(currentProStats: PassingStats) : this() {
        vision = currentProStats.vision
        crossing = currentProStats.crossing
        longPass = currentProStats.longPass
        shortPass = currentProStats.shortPass
        curve = currentProStats.curve
    }

    fun combine(traitStats: List<PassingStats>): PassingStats {
        val combinedStats = PassingStats()
        combinedStats.vision = vision
        combinedStats.crossing = crossing
        combinedStats.longPass = longPass
        combinedStats.shortPass = shortPass
        combinedStats.curve = curve

        traitStats.forEach {
            combinedStats.vision += it.vision
            combinedStats.crossing += it.crossing
            combinedStats.longPass += it.longPass
            combinedStats.shortPass += it.shortPass
            combinedStats.curve += it.curve
        }
        return combinedStats
    }
}

@Entity
data class ShootingStats(
    @ColumnInfo(name = "finishing") var finishing: Int = 0,
    @ColumnInfo(name = "fkAccuracy") var fkAccuracy: Int = 0,
    @ColumnInfo(name = "headingAccuracy") var headingAccuracy: Int = 0,
    @ColumnInfo(name = "shotPower") var shotPower: Int = 0,
    @ColumnInfo(name = "longShot") var longShot: Int = 0,
    @ColumnInfo(name = "volleys") var volleys: Int = 0,
    @ColumnInfo(name = "penalties") var penalties: Int = 0,
    @ColumnInfo(name = "weakFoot") var weakFoot: Int = 3
) {

    constructor(currentProStats: ShootingStats) : this() {
        finishing = currentProStats.finishing
        fkAccuracy = currentProStats.fkAccuracy
        headingAccuracy = currentProStats.headingAccuracy
        shotPower = currentProStats.shotPower
        longShot = currentProStats.longShot
        volleys = currentProStats.volleys
        penalties = currentProStats.penalties
        weakFoot = currentProStats.weakFoot
    }

    fun combine(traitStats: List<ShootingStats>): ShootingStats {
        val combinedStats = ShootingStats()
        combinedStats.finishing = finishing
        combinedStats.fkAccuracy = fkAccuracy
        combinedStats.headingAccuracy = headingAccuracy
        combinedStats.shotPower = shotPower
        combinedStats.longShot = longShot
        combinedStats.volleys = volleys
        combinedStats.penalties = penalties
        combinedStats.weakFoot = weakFoot

        traitStats.forEach {
            combinedStats.finishing += it.finishing
            combinedStats.fkAccuracy += it.fkAccuracy
            combinedStats.headingAccuracy += it.headingAccuracy
            combinedStats.shotPower += it.shotPower
            combinedStats.longShot += it.longShot
            combinedStats.volleys += it.volleys
            combinedStats.penalties += it.penalties
            combinedStats.weakFoot += it.weakFoot
        }
        return combinedStats
    }
}

@Entity
data class PaceStats(
    @ColumnInfo(name = "acceleration") var acceleration: Int = 0,
    @ColumnInfo(name = "sprintSpeed") var sprintSpeed: Int = 0
) {

    constructor(currentProStats: PaceStats) : this() {
        acceleration = currentProStats.acceleration
        sprintSpeed = currentProStats.sprintSpeed
    }

    fun combine(traitStats: List<PaceStats>): PaceStats {
        val combinedStats = PaceStats()
        combinedStats.acceleration = acceleration
        combinedStats.sprintSpeed = sprintSpeed

        traitStats.forEach {
            combinedStats.acceleration += it.acceleration
            combinedStats.sprintSpeed += it.sprintSpeed
        }
        return combinedStats
    }
}

@Entity
data class GoalkeepingStats(
    @ColumnInfo(name = "gkDiving") var gkDiving: Int = 0,
    @ColumnInfo(name = "gkHandling") var gkHandling: Int = 0,
    @ColumnInfo(name = "gkKicking") var gkKicking: Int = 0,
    @ColumnInfo(name = "gkReflexes") var gkReflexes: Int = 0,
    @ColumnInfo(name = "gkPositioning") var gkPositioning: Int = 0
) {

    constructor(currentProStats: GoalkeepingStats) : this() {
        gkDiving = currentProStats.gkDiving
        gkHandling = currentProStats.gkHandling
        gkKicking = currentProStats.gkKicking
        gkReflexes = currentProStats.gkReflexes
        gkPositioning = currentProStats.gkPositioning
    }

    fun combine(traitStats: List<GoalkeepingStats>): GoalkeepingStats {
        val combinedStats = GoalkeepingStats()
        combinedStats.gkDiving = gkDiving
        combinedStats.gkHandling = gkHandling
        combinedStats.gkKicking = gkKicking
        combinedStats.gkReflexes = gkReflexes
        combinedStats.gkPositioning = gkPositioning

        traitStats.forEach {
            combinedStats.gkDiving += it.gkDiving
            combinedStats.gkHandling += it.gkHandling
            combinedStats.gkKicking += it.gkKicking
            combinedStats.gkReflexes += it.gkReflexes
            combinedStats.gkPositioning += it.gkPositioning
        }
        return combinedStats
    }
}