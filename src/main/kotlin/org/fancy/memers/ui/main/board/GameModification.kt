package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Entity
import org.hexworks.zircon.api.data.Position3D

sealed class GameModification {
    class Move(val entity: Entity, private val translation: Position3D) : GameModification() {
        val targetPosition: Position3D get() = entity.position.withRelative(translation)
    }

    /*
    Мб надо сделать приоритет атак.
    Строить priority queue из модификаций атак и применять их в порядке уменьшения в `GameArea.applyCurrentStep`
     */
    class Attack(val attacker: Entity, val victim: Entity) : GameModification()
    object Step : GameModification()

    // сюда пойдут спеллы отдельным классов
}
