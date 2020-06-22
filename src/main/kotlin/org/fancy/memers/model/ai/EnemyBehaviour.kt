package org.fancy.memers.model.ai

import org.fancy.memers.model.drawable.Enemy
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification

interface EnemyBehaviour {
    fun gameAreaModification(enemy: Enemy, gameArea: GameArea): GameModification?
}
