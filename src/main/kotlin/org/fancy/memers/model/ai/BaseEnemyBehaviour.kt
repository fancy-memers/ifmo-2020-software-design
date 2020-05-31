package org.fancy.memers.model.ai

import org.fancy.memers.model.drawable.Creature
import org.fancy.memers.model.drawable.Enemy
import org.fancy.memers.model.hvNeighbours
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.utils.Vector3D
import org.hexworks.zircon.api.data.Position3D

abstract class BaseEnemyBehaviour : EnemyBehaviour {
    // TODO: сюда можно пихнуть bfs для поиска пути для AI

    // куда происходит смещение `enemy`
    protected fun supposedDirection(enemy: Enemy, gameArea: GameArea): Position3D {
        val player = gameArea.world.player
        val vector = Vector3D.create(enemy.position, player.position)
        val direction = vector.with(1.0) // двигаемся по вектору
        val shifts = listOf(1 to 1, -1 to -1, 1 to -1, -1 to 1)
        val tr = shifts.find {
            enemy.position.plus(Position3D.create(it.first, it.second, 0)).withZ(0) ==
                    player.position.withZ(0)
        }
        if (tr != null) {
            return listOf(direction.copy(x = 0), direction.copy(y = 0)).random().endPoint
        }
        return direction.endPoint
    }

    override fun toString(): String = this::class.simpleName ?: error("Could not get simpleName")

    // сначала ищется таргет из возможных конфликтов по позиции, потом в радиусе 1 клетка
    protected fun supposedTarget(enemy: Enemy, gameArea: GameArea): Creature? {
        // TODO: можно вынести в Entity, типо range атаки
        val attackRangePositions = enemy.position.hvNeighbours().toSet().minus(enemy.position)

        val board = gameArea.world.board.toMutableMap()
        return attackRangePositions.mapNotNull { board[it] as? Creature }.firstOrNull()
    }
}
