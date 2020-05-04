package org.fancy.memers.model.generator

import org.fancy.memers.model.*
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class UniformBoardGenerator(
    private val boardSize: Size3D,
    private val fillRate: Double = 0.2,
    seed: Int? = null
) : BoardGenerator {
    init {
        check(0 < fillRate && fillRate < 1)
        check(boardSize.xLength > 2 && boardSize.yLength > 2 && boardSize.zLength > 0)
    }

    private val random: Random = seed?.let { Random(it) } ?: Random

    override fun generateMap(): Map<Position3D, Block> {
        val pairs = boardSize.fetchPositions()
            .map { Position3D.create(it.x, it.y, 0) }
            .map { it to randomBlock(it) }
            .toList()
        val board = pairs.toMap().toMutableMap()
        val playerPosition = randomPlayerPosition()
        board[playerPosition] = Player(playerPosition)
        return board
    }

    private fun randomPlayerPosition(): Position3D {
        return Position3D.create(
            random.nextInt(boardSize.xLength),
            random.nextInt(boardSize.yLength),
            boardSize.zLength - 1
        )
    }

    private fun randomBlock(position: Position3D): Block {
        val isSafePositions = position
            .contains2D(
                IntRange(0, boardSize.xLength),
                IntRange(0, boardSize.yLength)
            )
        return if (isSafePositions && random.nextDouble() >= fillRate) {
            Empty(position)
        } else {
            Wall(position)
        }
    }
}
