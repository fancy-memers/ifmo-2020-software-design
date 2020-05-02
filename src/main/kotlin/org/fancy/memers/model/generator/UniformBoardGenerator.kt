package org.fancy.memers.model.generator

import org.fancy.memers.model.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.border
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.streams.toList

class UniformBoardGenerator(
    private val boardSize: Size,
    private val fillRate: Double = 0.2,
    seed: Int? = null
) : BoardGenerator {
    init {
        check(0 < fillRate && fillRate < 1)
        check(boardSize.height > 2 && boardSize.width > 2)
    }

    private val random: Random = seed?.let { Random(it) } ?: Random

    override fun generateMap(): Map<Position, Block> {
        val pairs = boardSize.fetchPositions()
            .map { Position(it.x, it.y) }
            .map { it to randomBlock(it) }
            .toList()
        val board = pairs.toMap().toMutableMap()
        val playerPosition = randomPosition()
        board[playerPosition] = Player(playerPosition)
        return board
    }


    private fun randomPosition(): Position {
        return Position(random.nextInt(boardSize.width), random.nextInt(boardSize.height))
    }

    private fun randomBlock(position: Position): Block {
        val isSafePositions = position
            .contains(
                IntRange(0, boardSize.width),
                IntRange(0, boardSize.height)
            )
        return if (isSafePositions && random.nextDouble() >= fillRate) {
            Empty(position)
        } else {
            Wall(position)
        }
    }
}