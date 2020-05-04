package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
import org.fancy.memers.model.generator.BoardGenerator
import org.fancy.memers.model.generator.UniformBoardGenerator
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea

/// `in-memory representation of our world.`
class GameArea(
    visibleSize: Size3D,
    generator: BoardGenerator = UniformBoardGenerator(visibleSize)
) : BaseGameArea<Tile, org.hexworks.zircon.api.data.Block<Tile>>(
    initialVisibleSize = visibleSize,
    initialActualSize = visibleSize
) {
    // actual model
    private val boardMap: MutableMap<Position3D, Block> = generator.generateMap().toMutableMap()
    private var player = boardMap.values.find { it is Player } as Player

    fun movePlayer(diff: Position3D) {
        val targetPosition = player.position.withRelative(diff).withZ(0)
        val targetBlock = boardMap[targetPosition] ?: return
        if (targetBlock !is Empty && targetBlock !is Floor) {
            return
        }
        setBlockAt(player.position, GameBlock(Tile.empty()))
        boardMap.remove(player.position)
        player = player.cloneWithPosition(targetPosition.withZ(player.position.z)) as Player
        setBlockAt(player.position, GameBlock(createTile(player)))
        boardMap[player.position] = player
    }

    fun getBoardMap(): Map<Position3D, Block> = boardMap

    init {
        for ((position, block) in boardMap) {
            val tile = createTile(block) // TODO: Factory
            val gameBlock = GameBlock(tile)
            setBlockAt(Position3D.create(position.x, position.y, position.z), gameBlock)
        }
    }

    companion object {
        fun createTile(block: Drawable): Tile {
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
