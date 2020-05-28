package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
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

        removeCreature(creature.position)
        setCreature(newPosition, creature)
    }

    fun attack(creature: Creature, targetCreature: Creature) {
        targetCreature.health -= creature.attack
        if (targetCreature.health <= 0) {
            enemies.remove(targetCreature)
            removeCreature(targetCreature.position)
        }
    }

    companion object {
        // do not remove even if empty, needed to allow `World.deserialize(...)`
    }
}
