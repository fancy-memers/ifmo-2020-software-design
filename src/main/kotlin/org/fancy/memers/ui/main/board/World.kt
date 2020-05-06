package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class World(
    val size: Size3D,
    val board: MutableMap<Position3D, Block>
) {

    // This should be fixed at generator level
    private val player: Player = board.values.filterIsInstance<Player>().single()
    private val enemies: MutableList<Enemy> = board.values.filterIsInstance<Enemy>().toMutableList()
    private val blockChangeEventHandlers = mutableListOf<(Position3D) -> Unit>()

    fun addBlockChangeEventHandler(handler: (Position3D) -> Unit) {
        blockChangeEventHandlers.add(handler)
    }

    private fun triggerBlockChangeEvent(position: Position3D) {
        blockChangeEventHandlers.forEach { it(position) }
    }

    private fun setEntity(position: Position3D, entity: Entity) {
        board[position] = entity
        entity.position = position
        triggerBlockChangeEvent(position)
    }

    private fun removeEntity(position: Position3D) {
        board.remove(position)
        triggerBlockChangeEvent(position)
    }

    fun movePlayer(diff: Position3D) {
        moveEntity(player, diff)
        updateWorld()
    }

    private fun moveEntity(entity: Entity, diff: Position3D) {
        val oldEntityPosition = entity.position
        val newEntityPosition = oldEntityPosition.withRelative(diff)

        val targetEntity = board[newEntityPosition]
        when (targetEntity) {
            is Empty? -> doMove(entity, newEntityPosition)
            is Entity -> doAttack(entity, targetEntity)
            else -> error("Unexpected block at $newEntityPosition: $targetEntity")
        }
    }

    private fun doMove(entity: Entity, newEntityPosition: Position3D) {
        val targetBlockPosition = newEntityPosition.withZ(0)
        val targetBlock = board[targetBlockPosition] ?: return
        if (targetBlock !is Empty && targetBlock !is Floor) return

        removeEntity(entity.position)
        setEntity(newEntityPosition, entity)
    }

    private fun doAttack(entity: Entity, targetEntity: Entity) {
        targetEntity.health -= entity.attack
        if (targetEntity.health < 0) {
            when (targetEntity) {
                is Enemy -> enemies.remove(targetEntity)
                is Player -> gameOver()
            }
            removeEntity(targetEntity.position)
        }
    }

    private fun updateWorld() {
        for (enemy in enemies) {
            // todo паттерн стратегия
            val direction = DIRECTIONS.random()
            moveEntity(enemy, direction)
        }
    }

    private fun gameOver() {
        TODO("not implemented")
    }

    // do not remove even if empty, needed to allow `World.deserialize(...)`
    companion object {
        val DIRECTIONS: Array<Position3D> = arrayOf(
            Position3D.create(0, -1, 0),
            Position3D.create(0, +1, 0),
            Position3D.create(-1, 0, 0),
            Position3D.create(+1, 0, 0)
        )
    }
}
