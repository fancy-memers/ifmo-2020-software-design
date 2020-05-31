package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Creature
import org.hexworks.zircon.api.data.Position3D

sealed class GameModification {
    class Move(val creature: Creature, private val translation: Position3D) : GameModification() {
        val targetPosition: Position3D get() = creature.position.withRelative(translation)
    }

    /*
    Мб надо сделать приоритет атак.
    Строить priority queue из модификаций атак и применять их в порядке уменьшения в `GameArea.applyCurrentStep`
     */
    class Attack(val attacker: Creature, val victim: Creature) : GameModification()
    object Step : GameModification()

    // TODO: сюда пойдут спеллы отдельным классов, типо SpellAttack ...
}
