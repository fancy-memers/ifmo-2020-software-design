package org.fancy.memers.ui.main.board

import org.fancy.memers.model.drawable.Creature
import org.fancy.memers.model.drawable.Item
import org.fancy.memers.model.hvNeighbours
import org.hexworks.zircon.api.data.Position3D

sealed class GameModification {
    object Identity : GameModification()

    abstract class BaseMove(val creature: Creature) : GameModification() {
        abstract val targetPosition: Position3D
    }

    open class Move(creature: Creature, val translation: Position3D) : BaseMove(creature) {
        override val targetPosition: Position3D
            get() = creature.position.withRelative(translation)
    }

    class ConfusedMove(creature: Creature) : BaseMove(creature) {
        override val targetPosition: Position3D
            get() = creature.position.hvNeighbours().random()
    }

    abstract class BaseAttack(val attacker: Creature, val victim: Creature) : GameModification()

    open class Attack(attacker: Creature, victim: Creature) : BaseAttack(attacker, victim)
    object Step : GameModification()

    class DropItem(val creature: Creature, val item: Item) : GameModification()

    class ConfusionSpellAttack(attacker: Creature, victim: Creature) : BaseAttack(attacker, victim)
}
