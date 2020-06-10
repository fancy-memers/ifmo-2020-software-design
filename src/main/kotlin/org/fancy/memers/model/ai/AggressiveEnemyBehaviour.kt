package org.fancy.memers.model.ai

import org.fancy.memers.model.drawable.Creature
import org.fancy.memers.model.drawable.Enemy
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification
import kotlin.random.Random

class AggressiveEnemyBehaviour : BaseEnemyBehaviour() {
    override fun gameAreaModification(enemy: Enemy, gameArea: GameArea): GameModification? {
        val direction = supposedDirection(enemy, gameArea)
        val victim = supposedTarget(enemy, gameArea)
        val attack = victim?.let {
            selectRandomAttack(enemy, it)
        }
        val movement = GameModification.Move(enemy, direction)
        return attack ?: movement
    }


    private fun selectRandomAttack(enemy: Enemy, creature: Creature): GameModification.BaseAttack {
        return if (Random.nextFloat() >= (1.0 - CONFUSION_PROBABILITY)) {
            GameModification.ConfusionSpellAttack(enemy, creature)
        }
        else {
            GameModification.Attack(enemy, creature)
        }
    }

    companion object {
        const val CONFUSION_PROBABILITY = 0.3
    }
}
