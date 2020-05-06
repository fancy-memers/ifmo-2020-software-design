package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Drawable
import org.fancy.memers.model.Empty
import org.fancy.memers.model.Floor
import org.fancy.memers.model.Player
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
    private val blockChangeEventHandlers = mutableListOf<(Position3D) -> Unit>()

    init {
//        actualBoard.remove(player.position)
    }

    fun addBlockChangeEventHandler(handler: (Position3D) -> Unit) {
        blockChangeEventHandlers.add(handler)
    }

    private fun triggerBlockChangeEvent(position: Position3D) {
        blockChangeEventHandlers.forEach { it(position) }
    }

    fun movePlayer(diff: Position3D) {
        val positionOld = player.position
        val positionNew = positionOld.withRelative(diff)

        val targetPosition = positionNew.withZ(0)
        val targetBlock = actualBoard[targetPosition] ?: return
        if (targetBlock !is Empty && targetBlock !is Floor) {
            return
        }

        actualBoard.remove(positionOld)
        actualBoard[positionNew] = player
        player.position = positionNew

        triggerBlockChangeEvent(positionOld)
        triggerBlockChangeEvent(positionNew)
    }
}

class GameArea(val world: World):
    BaseGameArea<Tile, GameAreaBlock<Tile>>(initialVisibleSize = world.size, initialActualSize = world.size) {

    init {
        world.addBlockChangeEventHandler { updateBlock(it) }
        for (position in world.actualBoard.keys) {
            updateBlock(position)
        }
    }

    private fun updateBlock(position: Position3D) {
        val block = world.actualBoard[position]
        val gameBlock = if (block != null) GameBlock(createTile(block)) else EMPTY_TILE_BLOCK
        setBlockAt(position, gameBlock)
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
