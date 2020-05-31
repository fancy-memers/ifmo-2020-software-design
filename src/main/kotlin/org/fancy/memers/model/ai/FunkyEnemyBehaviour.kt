package org.fancy.memers.model.ai

import org.fancy.memers.model.Enemy
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification
import org.fancy.memers.utils.Vector3D

class FunkyEnemyBehaviour : BaseEnemyBehaviour() {
    override fun gameAreaModification(enemy: Enemy, gameArea: GameArea): GameModification? {
        val player = gameArea.world.player
        val vector = Vector3D.create(enemy.position, player.position)
        val direction = vector.with(-1.0)

        val victim = supposedTarget(enemy, gameArea)
        val attack = victim?.let { GameModification.Attack(enemy, it) }
        val movement = GameModification.Move(enemy, direction.endPoint)
        return attack ?: movement
    }
}

