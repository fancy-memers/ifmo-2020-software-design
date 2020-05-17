package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class World(
    val size: Size3D,
    val board: MutableMap<Position3D, Block>
) {

    // This should be fixed at generator level
    val player: Player = board.values.filterIsInstance<Player>().single()
    val enemies: MutableList<Enemy> = board.values.filterIsInstance<Enemy>().toMutableList()


    private fun setEntity(position: Position3D, entity: Entity) {
        board[position] = entity
        entity.position = position
    }

    private fun removeEntity(position: Position3D) {
        board.remove(position)
    }

    fun move(entity: Entity, newPosition: Position3D) {
        val groundPosition = newPosition.withZ(0)
        val groundBlock = board[groundPosition] ?: return
        val targetBlock = board[newPosition]

        // надо что-го говорить в таком случае
        // или придумать какое-то другое поведение
        if (!groundBlock.canStepOn || targetBlock?.canStepOn == false) return

        removeEntity(entity.position)
        setEntity(newPosition, entity)
    }

    fun attack(entity: Entity, targetEntity: Entity) {
        targetEntity.health -= entity.attack
        if (targetEntity.health < 0) {
            enemies.remove(targetEntity)
            removeEntity(targetEntity.position)
        }
    }


    companion object {
        // do not remove even if empty, needed to allow `World.deserialize(...)`
    }
}
