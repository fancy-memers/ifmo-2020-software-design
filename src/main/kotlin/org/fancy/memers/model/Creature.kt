package org.fancy.memers.model

import org.fancy.memers.model.ai.EnemyBehaviour
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.graphics.Symbols
import java.util.*

sealed class Creature(var position: Position3D, open val defence: Int = 0): Block() {
    override fun equals(other: Any?): Boolean = super.equals(other) && position == (other as Creature).position
    override fun hashCode(): Int = Objects.hashCode(position)
    override fun toString(): String {
        return "Entity(level=$level, health=$health)"
    }

    open val attackMultiplier: Int = 2

    open var level: Int = 1
    open var experience: Int = 0

    open val givesExperience: Int
        get() = Math.pow(2.0, level.toDouble()).toInt() + experience

    protected open fun increaseLevel() {
        level += 1
        strength += 4
        agility += 4
        intelligence += 4
        health = maxHealth
    }

    open fun gainExperience(amount: Int): Boolean {
        val breakpoint = Math.pow(2.0, level.toDouble()).toInt()
        if (experience + amount >= breakpoint) {
            experience += amount - breakpoint
            increaseLevel()
            return true
        }
        experience += amount
        return false
    }

    open var strength: Int = DEFAULT_STRENGTH
    open var agility: Int = DEFAULT_AGILITY
    open var intelligence: Int = DEAFULT_INTELLIGENCE

    open val maxHealth: Int
        get() = strength * 4

    open var health: Int = maxHealth

    open val attack: Int
        get() = strength * attackMultiplier

    open val effects = mutableListOf<Effect>()

    open fun updateEffects() {
        effects.forEach { it.duration -= 1 }
        effects.removeIf { !it.isActive }
    }

    protected val initialHealth = health
    val isDead: Boolean get() = health <= 0

    override val canStepOn: Boolean
        get() = false
    override val isVisible: Boolean
        get() = !isDead

    inline fun <reified EffectType: Effect> hasEffect() = effects.indexOfFirst { it is EffectType } != -1

    companion object {
        const val DEFAULT_STRENGTH: Int = 5
        const val DEFAULT_AGILITY: Int = 5
        const val DEAFULT_INTELLIGENCE: Int = 5
    }
}

class Player(position: Position3D) : Creature(position) {
    val inventory: Inventory = Inventory()

    override val attack: Int get() = super.attack + inventory.activeItems.sumBy { it.attackBonus }
    override val defence: Int get() = inventory.activeItems.sumBy { it.attackBonus }

    override val symbol: Char get() = '@'

    override fun toString(): String = "Player(level=$level, health=$health, effects=$effects)"

//    init {
//        repeat(4) { increaseLevel() }
//    }

    /*
     * Считает текущий цвет
     * minimumColor – изначальный цвет
     * maximumColor – цвет при 0 значении health
     */
    override val foregroundColor: TileColor
        get() = gradientColor(
            TileColor.fromString("#00ff00"),
            TileColor.fromString("#ffff00"),
            -(initialHealth - health) / initialHealth.toDouble()
        )
    override val backgroundColor: TileColor get() = TileColor.fromString("#1e2320")
    override val displayName: String
        get() = "Player"
}

class Enemy(private val name: String, val behaviour: EnemyBehaviour, position: Position3D) : Creature(position) {
    override val symbol: Char get() = Symbols.EPSILON

    override fun toString(): String =
        "Enemy(level=$level, health=$health, behaviour=$behaviour, effects=$effects)"

    override val attackMultiplier: Int = 1

    /*
     * Считает текущий цвет
     * minimumColor – изначальный цвет
     * maximumColor – цвет при 0 значении health
     */
    override val foregroundColor: TileColor
        get() = gradientColor(
            minimumColor = TileColor.fromString("#ff0000"),
            maximumColor = TileColor.fromString("#ffff00"),
            ratio = (initialHealth - health) / initialHealth.toDouble()
        )
    override val backgroundColor: TileColor get() = TileColor.fromString("#1e2320")
    override val displayName: String
        get() = name
}
