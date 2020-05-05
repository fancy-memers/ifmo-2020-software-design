package org.fancy.memers.ui.main.board

import org.fancy.memers.model.*
import org.fancy.memers.model.generator.CellularAutomataBoardGenerator
import org.fancy.memers.model.generator.BoardGenerator
import org.fancy.memers.model.generator.UniformBoardGenerator
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.base.BaseGameArea



/// `in-memory representation of our world.`
class GameArea(
    visibleSize: Size3D,
    generator: BoardGenerator = CellularAutomataBoardGenerator(visibleSize, 8, UniformBoardGenerator(visibleSize, 0.5))
) : BaseGameArea<Tile, org.hexworks.zircon.api.data.Block<Tile>>(
    initialVisibleSize = visibleSize,
    initialActualSize = visibleSize
) {
    // actual model
    private val boardMap: MutableMap<Position3D, Block> = generator.generateMap().toMutableMap()
    private val player = boardMap.values.find { it is Player } as Player
    private val playerGameBlock = GameBlock(createTile(player))

    fun movePlayer(diff: Position3D) {
        val targetPosition = player.position.withRelative(diff).withZ(0)
        val targetBlock = boardMap[targetPosition] ?: return
        if (targetBlock !is Empty && targetBlock !is Floor) {
            return
        }
        setBlockAt(player.position, GameBlock(Tile.empty()))
        player.position = targetPosition.withZ(player.position.z)
        setBlockAt(player.position, playerGameBlock)
    }

    fun getBoardMap(): Map<Position3D, Block> = boardMap

    init {
        boardMap.remove(player.position)
        setBlockAt(player.position, playerGameBlock)
        for ((position, block) in boardMap) {
            val tile = createTile(block) // TODO: Factory
            val gameBlock = GameBlock(tile)
            setBlockAt(position, gameBlock)
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
