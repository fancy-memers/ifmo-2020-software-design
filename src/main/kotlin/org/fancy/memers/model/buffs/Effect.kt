package org.fancy.memers.model.buffs

import org.fancy.memers.model.drawable.Creature
import org.fancy.memers.ui.main.board.GameModification

abstract class Effect {
    open var duration: Int = 0

    val isActive
        get() = duration > 0

    override fun toString(): String =
        "${this::class.simpleName ?: error("Could not get simpleName")}(duration=$duration)"

    abstract val displayName: String

    abstract fun createModification(creature: Creature): GameModification
}

class ConfusionEffect : Effect() {
    override var duration: Int = INITIAL_DURATION

    override fun createModification(creature: Creature): GameModification =
        GameModification.ConfusedMove(creature)

    override val displayName: String
        get() = "confusion"

    companion object {
        const val INITIAL_DURATION = 5
    }
}

