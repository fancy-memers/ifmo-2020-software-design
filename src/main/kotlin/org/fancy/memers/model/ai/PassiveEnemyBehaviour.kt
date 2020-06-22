package org.fancy.memers.model.ai

import org.fancy.memers.model.drawable.Enemy
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification

class PassiveEnemyBehaviour : EnemyBehaviour {
    override fun gameAreaModification(enemy: Enemy, gameArea: GameArea): GameModification? = null
}
