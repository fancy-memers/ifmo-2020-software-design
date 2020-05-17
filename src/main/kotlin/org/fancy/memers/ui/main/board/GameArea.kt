package org.fancy.memers.ui.main.board

import kotlinx.collections.immutable.toImmutableList
import org.fancy.memers.model.Drawable
import org.fancy.memers.model.Empty
import org.fancy.memers.utils.RogueBaseGameArea
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Block as GameAreaBlock

class GameArea(val world: World) :
    RogueBaseGameArea<Tile, GameAreaBlock<Tile>>(initialVisibleSize = world.size, initialActualSize = world.size) {
    /*
        Запоминаем модификации мира на текущем шаге
        Needed for:
        При попытке занять одну клетку существа атакуют друг друга
     */
    val currentStepModifications = StepModifications()

    init {
        reloadGameArea()
    }

    // внешняя функция для отклика на модификацию
    fun apply(modification: GameModification) {
        currentStepModifications.add(modification)
        if (modification is GameModification.Step) {
            makeStep()
        }
    }

    // функция, которая делает нужный стафф в конце степа
    private fun makeStep() {
        makeAIModification()

        applyCurrentStep(currentStepModifications)
        reloadGameArea()
        currentStepModifications.clear()

        if (world.player.isDead) {
            gameOver()
        }
        println(world.player)
        println(world.enemies)
        println()
    }

    // function to make enemies move/attack etc.
    private fun makeAIModification() {
        val enemies = world.enemies.toImmutableList()
        for (enemy in enemies) {
            val behaviour = enemy.behaviour
            val modification = behaviour.gameAreaModification(enemy, this) ?: continue
            apply(modification)
        }
    }

    // применяет все модификации игрового мира
    private fun applyCurrentStep(modifications: StepModifications) {
        for (modification in modifications.modifications) {
            when (modification) {
                is GameModification.Move -> world.move(modification.entity, modification.targetPosition)
                is GameModification.Attack -> world.attack(modification.attacker, modification.victim)
                is GameModification.Step -> {}
            }
        }
    }

    // обновляет view
    private fun reloadGameArea() {
        clearBlocks()
        for ((position, block) in world.board) {
            val gameBlock = GameBlock(createTile(block))
            setBlockAt(position, gameBlock)
        }
    }

    private fun gameOver() {
        TODO("not implemented")
    }

    companion object {

        private fun createTile(block: Drawable): Tile {
            return when (block) {
                is Empty -> Tile.empty()
                else -> Tile.newBuilder()
                    .withCharacter(block.symbol)
                    .withForegroundColor(block.foregroundColor)
                    .withBackgroundColor(block.backgroundColor)
                    .buildCharacterTile()
            }
        }
    }
}
