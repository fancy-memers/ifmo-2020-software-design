package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Creature
import org.fancy.memers.model.hvNeighbours
import org.hexworks.zircon.api.data.Position3D

sealed class GameModification {
    abstract class BaseMove(val creature: Creature): GameModification() {
        abstract val targetPosition: Position3D
    }

    open class Move(creature: Creature, val translation: Position3D): BaseMove(creature) {
        override val targetPosition: Position3D
            get() = creature.position.withRelative(translation)
    }

    class ConfusedMove(creature: Creature): BaseMove(creature) {
        override val targetPosition: Position3D
            get() = creature.position.hvNeighbours().random()
    }

    /*
    Мб надо сделать приоритет атак.
    Строить priority queue из модификаций атак и применять их в порядке уменьшения в `GameArea.applyCurrentStep`
     */
    abstract class BaseAttack(val attacker: Creature, val victim: Creature): GameModification()

    open class Attack(attacker: Creature, victim: Creature): BaseAttack(attacker, victim)
    object Step : GameModification()

    // TODO: сюда пойдут спеллы отдельным классов, типо SpellAttack ...

    class ConfusionSpellAttack(attacker: Creature, victim: Creature): BaseAttack(attacker, victim)
}
