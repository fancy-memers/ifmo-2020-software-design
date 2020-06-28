package org.fancy.memers.ui.main.board

import kotlinx.collections.immutable.toImmutableList
import org.fancy.memers.model.drawable.*
import org.fancy.memers.ui.main.WorldUpdate
import org.fancy.memers.ui.start.GameOverMenuView
import org.fancy.memers.utils.RogueBaseGameArea
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Block as GameAreaBlock

/**
 * Класс требуемый фреймворком (zircon)
 * По сути обёртка над World, преобразующая наши блоки модели (Block) в блоки представления (view) фреймворка (GameBlock)
 * И обновляющая view при изменениях модели
 */
class GameArea(val world: World) :
    RogueBaseGameArea<Tile, GameAreaBlock<Tile>>(
        initialVisibleSize = world.boardSize,
        initialActualSize = world.boardSize
    ) {
    init {
        reloadGameArea()
    }

    // внешняя функция для отклика на модификацию
    fun apply(modification: GameModification) {
        when (modification) {
            is GameModification.BaseMove -> {
                val targetCreature = world[modification.targetPosition] as? Creature
                val actualModification = when (modification.creature) {
                    is ConfusedPlayer -> GameModification.ConfusedMove(modification.creature)
                    else -> modification
                }
                if (actualModification.creature is Player && targetCreature != null) {
                    world.attack(actualModification.creature, targetCreature)
                } else {
                    world.move(actualModification.creature, actualModification.targetPosition)
                }
            }
            is GameModification.Attack -> {
                world.attack(modification.attacker, modification.victim)
            }
            is GameModification.ConfusionSpellAttack -> world.confuse(modification.attacker, modification.victim)
            is GameModification.Step -> makeStep()
            is GameModification.Identity -> Unit
            is GameModification.DropItem -> world.drop(modification.creature, modification.item)
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Обновляет блок view на основе блока модели
     */
    // функция, которая делает нужный стафф в конце степа
    private fun makeStep() {
        makeAIModification()
        applyEffects()
        reloadGameArea()
        WorldUpdate.publish(world)

        // FIXME: remove DEBUG logging
        println("Player: ${world.player}")
        println("Enemies: ${world.enemies}")
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

    private fun applyCreatureEffects(creature: Creature) {
        creature.effects.map {
            it.createModification(creature)
        }.forEach { apply(it) }
    }

    private fun applyEffects() {
        world.enemies.forEach { applyCreatureEffects(it) }
        applyCreatureEffects(world.player)
        world.enemies.forEach { it.updateEffects() }
        world.player.run {
            updateEffects()
            if (isConfused && confusionDuration <= 0) {
                world.setCreature(position, (this as ConfusedPlayer).actualPlayer)
            }
        }
    }

    // обновляет view
    private fun reloadGameArea() {
        clearBlocks()
        for ((position, block) in world.board.filter { it.value.isVisible }) {
            val gameBlock = GameBlock(createTile(block))
            setBlockAt(position, gameBlock)
        }
    }

    val gameIsOver
        get() = world.player.isDead

    companion object {

        fun createTile(block: Drawable): Tile {
            return when (block) {
                is Empty -> Tile.empty()
                is Enemy -> Tile.newBuilder()
                    .withCharacter(block.symbol)
                    .withForegroundColor(block.foregroundColor)
                    .withBackgroundColor(block.backgroundColor)
                    .buildCharacterTile()
                else -> Tile.newBuilder()
                    .withCharacter(block.symbol)
                    .withForegroundColor(block.foregroundColor)
                    .withBackgroundColor(block.backgroundColor)
                    .buildCharacterTile()
            }
        }
    }
}
