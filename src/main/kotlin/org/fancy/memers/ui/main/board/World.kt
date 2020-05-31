package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
import org.fancy.memers.ui.main.WorldUpdate
import org.fancy.memers.utils.logger.log
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class World(
    val boardSize: Size3D,
    val board: MutableMap<Position3D, Block>
) {
    // This should be fixed at generator level
    val player: Player = board.values.filterIsInstance<Player>().single()
    val enemies: MutableList<Enemy> = board.values.filterIsInstance<Enemy>().toMutableList()

    private fun setCreature(position: Position3D, creature: Creature) {
        board[position] = creature
        creature.position = position
    }

    operator fun get(key: Position3D): Block? = board[key]

    private fun removeCreature(position: Position3D) {
        board.remove(position)
    }

    fun move(creature: Creature, newPosition: Position3D) {
        val groundPosition = newPosition.withZ(0)
        val groundBlock = board[groundPosition] ?: return
        val targetBlock = board[newPosition]

        // надо что-го говорить в таком случае
        // или придумать какое-то другое поведение
        if (!groundBlock.canStepOn || targetBlock?.canStepOn == false) return

        if (creature is Player && groundBlock is Floor) {
            groundBlock.item?.let {
                creature.inventory.items += it
                groundBlock.item = null
            }
        }

        removeCreature(creature.position)
        setCreature(newPosition, creature)
    }

    fun gainExperience(creature: Creature, amount: Int) {
        log("${creature.displayName} gains ${amount} exp")
        if (creature.gainExperience(amount)) {
            log("${creature.displayName} is now level ${creature.level}")
        }
    }

    fun attack(creature: Creature, targetCreature: Creature) {
        val damage = creature.attack - targetCreature.defence
        targetCreature.health -= damage
        log("${creature.displayName} attacks ${targetCreature.displayName} for $damage hp")
        if (targetCreature.isDead) {
            log("${targetCreature.displayName} is dead")
            gainExperience(creature, targetCreature.givesExperience)
            enemies.remove(targetCreature)
            removeCreature(targetCreature.position)
        }
        if (creature is Player) {
            WorldUpdate.publish(this)
        }
    }

    fun confuse(creature: Creature, targetCreature: Creature) {
        when (val effectIndex = targetCreature.effects.indexOfFirst { it is ConfusionEffect }) {
            -1 -> {
                log("${creature.displayName} confuses ${targetCreature.displayName}")
                targetCreature.effects.add(ConfusionEffect())
            }
            else -> {
                log("${creature.displayName} updates confusion ${targetCreature.displayName}")
                targetCreature.effects[effectIndex] = ConfusionEffect()
            }
        }
    }

    companion object {
        // do not remove even if empty, needed to allow `World.deserialize(...)`
    }
}
