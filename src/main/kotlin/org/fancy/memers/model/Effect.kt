package org.fancy.memers.model

import org.fancy.memers.ui.main.board.GameModification

abstract class Effect {
    open var duration: Int = 0

    val isActive
        get() = duration > 0

    override fun toString(): String =
        "${this::class.simpleName ?: error("Could not get simpleName")}(duration=$duration)"

    abstract fun createModification(creature: Creature): GameModification
}

class ConfusionEffect: Effect() {
    override var duration: Int = INITAL_DURATION

    override fun createModification(creature: Creature): GameModification =
        GameModification.ConfusedMove(creature)

    companion object {
        const val INITAL_DURATION = 5
    }
}

