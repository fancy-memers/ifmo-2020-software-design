package org.fancy.memers.model.ai

import org.fancy.memers.model.Enemy
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification

class AggressiveEnemyBehaviour : BaseEnemyBehaviour() {
    override fun gameAreaModification(enemy: Enemy, gameArea: GameArea): GameModification? {
        val direction = supposedDirection(enemy, gameArea)
        val victim = supposedTarget(enemy, gameArea)
        val attack = victim?.let { GameModification.Attack(enemy, it) }
        val movement = GameModification.Move(enemy, direction)
        return attack ?: movement
    }
}
