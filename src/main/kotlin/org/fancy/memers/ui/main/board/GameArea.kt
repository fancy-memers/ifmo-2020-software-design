package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Drawable
import org.fancy.memers.model.Empty
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.data.Block as GameAreaBlock

class GameArea(val world: World):
    BaseGameArea<Tile, GameAreaBlock<Tile>>(initialVisibleSize = world.size, initialActualSize = world.size) {

    init {
        world.addBlockChangeEventHandler { updateBlock(it) }
        for (position in world.board.keys) {
            updateBlock(position)
        }
    }

    private fun updateBlock(position: Position3D) {
        val block = world.board[position]
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
