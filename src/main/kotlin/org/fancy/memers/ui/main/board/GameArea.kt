package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
import org.fancy.memers.model.generator.BoardGenerator
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.data.Block as GameAreaBlock


class World(val size: Size3D, generator: BoardGenerator = BoardGenerator.defaultGenerator(size)) {
    val actualBoard = generator.generateMap().toMutableMap()
    // This should be fixed at generator level
    val player = actualBoard.values.find { it is Player } as Player
    private val blockChangeEventHandlers = mutableListOf<(Block) -> Unit>()

    init {
        actualBoard.remove(player.position)
    }

    fun addBlockChangeEventHandler(handler: (Block)-> Unit) {
        blockChangeEventHandlers.add(handler)
    }

    private fun triggerBlockChangeEvent(block: Block) {
        blockChangeEventHandlers.forEach { it(block) }
    }

    fun movePlayer(diff: Position3D) {
        val targetPosition = player.position.withRelative(diff).withZ(0)
        val targetBlock = actualBoard[targetPosition] ?: return
        if (targetBlock !is Empty && targetBlock !is Floor) {
            return
        }
        triggerBlockChangeEvent(Empty(player.position))
        player.position = targetPosition.withZ(player.position.z)
        triggerBlockChangeEvent(player)
    }
}

class GameArea(val world: World):
    BaseGameArea<Tile, GameAreaBlock<Tile>>(initialVisibleSize = world.size, initialActualSize = world.size) {
    private val gameBlocks =
        mapOf<Block, GameBlock>(
            Pair(world.player, GameBlock(createTile(world.player))),
            *(world.actualBoard.values.map { Pair(it, GameBlock(createTile(it))) }.toTypedArray())
        )

    init {
        world.addBlockChangeEventHandler {
            val gameBlock = gameBlocks.getOrDefault(it, EMPTY_TILE_BLOCK)
            setBlockAt(it.position, gameBlock)
        }
        for ((block, gameBlock) in gameBlocks) {
            setBlockAt(block.position, gameBlock)
        }
    }

    companion object {
        private val EMPTY_TILE_BLOCK = GameBlock(Tile.empty())

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
