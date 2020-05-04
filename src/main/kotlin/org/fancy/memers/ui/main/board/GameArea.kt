package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Drawable
import org.fancy.memers.model.Empty
import org.fancy.memers.model.generator.UniformBoardGenerator
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.game.base.BaseGameArea

/// `in-memory representation of our world.`
class GameArea(
    visibleSize: Size3D
) : BaseGameArea<Tile, Block<Tile>>(
    initialVisibleSize = visibleSize,
    initialActualSize = visibleSize
) {
    private val generator = UniformBoardGenerator(visibleSize) // TODO: DI

    init {
        val boardMap = generator.generateMap()
        for ((position, block) in boardMap) {
            val tile = createTile(block) // TODO: Factory
            val gameBlock = GameBlock(tile)
            setBlockAt(Position3D.create(position.x, position.y, 0), gameBlock)
        }
    }


    companion object {
        fun createTile(block: Drawable): Tile {
            return when (block) {
                is Empty -> Tile.empty()
                else -> Tile.newBuilder()
                    .withCharacter(block.symbol)
                    .withForegroundColor(block.foregroundColor)
                    .withBackgroundColor(block.foregroundColor)
                    .buildCharacterTile()
            }
        }
    }
}