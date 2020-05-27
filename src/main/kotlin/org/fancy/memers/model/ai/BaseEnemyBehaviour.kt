package org.fancy.memers.model.ai

import org.fancy.memers.model.Enemy
import org.fancy.memers.model.Entity
import org.fancy.memers.model.fetchNeighbours
import org.fancy.memers.model.hvNeighbours
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.utils.Vector3D
import org.hexworks.zircon.api.data.Position3D

abstract class BaseEnemyBehaviour : EnemyBehaviour {
    // TODO: сюда можно пихнуть bfs для поиска пути для AI

    // куда происходит сщенение `enemy`
    protected fun supposedDirection(enemy: Enemy, gameArea: GameArea): Position3D {
        val player = gameArea.world.player
        val vector = Vector3D.create(enemy.position, player.position)
        val direction = vector.with(1.0) // двигаемся по вектору
        return direction.endPoint
    }

    // сначала ищется таргет из возможных конфликтов по позиции, потом в радиусе 1 клетка
    protected fun supposedTarget(enemy: Enemy, gameArea: GameArea): Entity? {
        // TODO: можно вынести в Entity, типо range атаки
        val attackRangePositions = enemy.position.hvNeighbours().toSet().minus(enemy.position)

        val board = gameArea.world.board.toMutableMap()
        return attackRangePositions.mapNotNull { board[it] as? Entity }.firstOrNull()
    }
}
